package com.example.librarymanagement.ui.borrow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.borrow.Borrow
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.data.borrow.BorrowRequestDetailRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewBorrowRequestViewModel(
    private val borrowRequestDetailRepository: BorrowRequestDetailRepository,
    private val borrowRequestRepository: BorrowRequestRepository,
    private val borrowRepository: BorrowRepository
) : ViewModel() {
    var borrowUiState by mutableStateOf(BorrowUiState())
        private set

    fun updateUiState(borrowDetail: BorrowDetail) {
        borrowUiState = BorrowUiState(borrowDetail = borrowDetail, isBorrowValid = validateInput(borrowDetail))
    }

    private fun validateInput(uiState: BorrowDetail = borrowUiState.borrowDetail): Boolean {
        return with(uiState) {
            listBooks.isNotEmpty() && memberName.isNotBlank()
                    && borrowDate.isNotBlank() && exceptDate.isNotBlank()
        }
    }

    suspend fun saveBorrow() {
        withContext(Dispatchers.IO) {
            // Chạy song song việc insert borrow và insert borrowRequest
            val insertBorrowJob = async {
                borrowUiState.borrowDetail.toListBorrow().forEach { borrow ->
                    borrowRepository.insertBorrow(borrow)
                }
            }

            val insertBorrowRequestJob = async {
                borrowRequestRepository.insertBorrowRequest(
                    borrowUiState.borrowDetail.toBorrowRequest()
                )
            }

            // Đợi cả hai thao tác hoàn thành
            insertBorrowJob.await()
            insertBorrowRequestJob.await()
        }
    }

    fun showDialog() {
        borrowUiState = borrowUiState.copy(isShowDialog = !borrowUiState.isShowDialog)
    }
}

data class BorrowDetail(
    val borrowId : Int = 0,
    val listBookIds: List<String> = listOf(),
    val listBooks: List<String> = listOf(),
    val memberId: String = "",
    val memberName: String = "",
    val borrowDate: String = "",
    val exceptDate: String = "",
    val returnDate: String? = "",
    val state: String = "Chưa trả"
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
    bookCount = listBooks.size,
    exceptDate = exceptDate,
    returnDate = returnDate,
    state = state == "Đã trả"
)

fun BorrowDetail.toListBorrow(): List<Borrow> {
    return listBookIds.map {
        Borrow(borrowId = borrowId, bookId = it.toIntOrNull() ?: 0)
    }
}