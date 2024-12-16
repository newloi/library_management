package com.example.librarymanagement.ui.borrow

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.book.BookRepository
import com.example.librarymanagement.data.borrow.Borrow
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.data.borrow.BorrowRequestDetailRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import com.example.librarymanagement.data.member.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddNewBorrowRequestViewModel(
    private val borrowRequestRepository: BorrowRequestRepository,
    private val borrowRepository: BorrowRepository,
    private val bookRepository: BookRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {
    var borrowUiState by mutableStateOf(BorrowUiState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUiState(borrowDetail: BorrowDetail) {
        borrowUiState = BorrowUiState(borrowDetail = borrowDetail, isBorrowValid = validateInput(borrowDetail))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateInput(uiState: BorrowDetail = borrowUiState.borrowDetail): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Chuyển chuỗi thành LocalDate
        if(uiState.exceptDate.isNotBlank() && uiState.borrowDate.isNotBlank()){
            val except = LocalDate.parse(uiState.exceptDate, formatter)
            val borrow = LocalDate.parse(uiState.borrowDate, formatter)
            return with(uiState) {
                (memberName != "Mã thành viên không đúng!") && borrowDate.isNotBlank() && (countBook != 0)
                        && exceptDate.isNotBlank()
                    && (except > borrow)
            }
        }
        return false
//        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveBorrow() {
//        withContext(Dispatchers.IO) {
//            // Chạy song song việc insert borrow và insert borrowRequest
//            val insertBorrowJob = async {
//                borrowUiState.borrowDetail.toListBorrow().forEach { borrow ->
//                    borrowRepository.insertBorrow(borrow)
//                }
//            }
//
//            val insertBorrowRequestJob = async {
//                borrowRequestRepository.insertBorrowRequest(
//                    borrowUiState.borrowDetail.toBorrowRequest()
//                )
//            }
//
//            // Đợi cả hai thao tác hoàn thành
//            insertBorrowJob.await()
//            insertBorrowRequestJob.await()
//        }
        val borrowRequestID = borrowRequestRepository.insertBorrowRequest(
            borrowUiState.borrowDetail.toBorrowRequest()
        )
        updateUiState(borrowUiState.borrowDetail.copy(borrowId = borrowRequestID.toString().toInt()))
        borrowUiState.borrowDetail.toListBorrow().forEach { borrow ->
            borrowRepository.insertBorrow(borrow)
        }
    }

    suspend fun getBookName(bookId: Int): String {
        return bookRepository.getBookStream(bookId)
            .map { it?.name ?: "Mã sách không đúng!" }
            .first()
    }

    suspend fun getMemberName(memberId: Int): String {
        return memberRepository.getMemberStream(memberId)
            .map { it?.name ?: "Mã thành viên không đúng!" }
            .first()
    }

    fun showDialog() {
        borrowUiState = borrowUiState.copy(isShowDialog = !borrowUiState.isShowDialog)
    }

    suspend fun isOutOfStock(bookId: Int): Boolean {
        if(bookId == 0) return false;
        return bookRepository.getBookStream(bookId)
            .map { (it?.quantities ?: 1) <= 0 }
            .first()
    }

    suspend fun reduceQuantityByOne() {
            borrowUiState.borrowDetail.listBookIds.forEach {
                val currentBook = bookRepository
                    .getBookStream(it.toInt()).first()
                if (currentBook != null) {
                    bookRepository.updateBook(currentBook.copy(quantities = currentBook.quantities - 1))
                }
            }
    }
}

data class BorrowDetail(
    val borrowId : Int = 0,
    val listBookIds: List<String> = listOf(),
//    val listBooks: List<String> = listOf(),
    val memberId: String = "",
    val memberName: String = "",
    val countBook: Int = 0,
    val borrowDate: String = "",
    val exceptDate: String = "",
    val returnDate: String? = "",
    val state: String = "Chưa trả",
//    val countTotal: Int = 0
)

data class BorrowUiState(
    val isBorrowValid: Boolean = false,
    val borrowDetail: BorrowDetail = BorrowDetail(),
    val isShowDialog: Boolean = false
)

//fun BorrowDetail.toBorrowRequestDetail(): BorrowRequestDetail = BorrowRequestDetail(
//    borrowId = borrowId,
//    listBookIds = listBookIds.joinToString(separator = ","),
//    listBooks = listBooks.joinToString(separator = ","),
//    memberId = memberId.toIntOrNull() ?: 0,
//    memberName = memberName,
//    borrowDate = borrowDate,
//    exceptDate = exceptDate,
//    returnDate = returnDate,
//    state = state == "Đã trả"
//)
//
//fun BorrowRequestDetail.toBorrowDetail(): BorrowDetail = BorrowDetail(
//    borrowId = borrowId,
//    listBookIds = listBookIds.split(","),
//    listBooks = listBooks.split(","),
//    memberId = memberId.toString(),
//    memberName = memberName,
//    borrowDate = borrowDate,
//    exceptDate = exceptDate,
//    returnDate = returnDate,
//    state = if(state) "Đã trả" else "Chưa trả"
//)
//
//fun BorrowRequestDetail.toBorrowUiState(isBorrowValid: Boolean = false): BorrowUiState = BorrowUiState(
//    borrowDetail = this.toBorrowDetail(),
//    isBorrowValid = isBorrowValid
//)

fun BorrowDetail.toBorrowRequest(): BorrowRequest = BorrowRequest(
    memberId = memberId.toIntOrNull() ?: 0,
    borrowDate = borrowDate,
    bookCount = listBookIds.size,
    exceptDate = exceptDate,
    returnDate = returnDate,
    state = state == "Đã trả"
)

fun BorrowDetail.toListBorrow(): List<Borrow> {
    return listBookIds.map {
        Borrow(borrowId = borrowId, bookId = it.toIntOrNull() ?: 0)
    }
}

fun BorrowRequest.toBorrowDetail(): BorrowDetail = BorrowDetail(
    borrowId = id,
    listBookIds = listOf(),
    memberId = memberId.toString(),
    memberName = "AAAA",
    countBook = bookCount,
    borrowDate = borrowDate,
    exceptDate = exceptDate,
    returnDate = returnDate,
    state = if(state) "Đã trả" else "Chưa trả"
)

fun BorrowRequest.toBorrowUiState(isBorrowValid: Boolean = false): BorrowUiState = BorrowUiState(
    isBorrowValid = isBorrowValid,
    borrowDetail = this.toBorrowDetail()
)