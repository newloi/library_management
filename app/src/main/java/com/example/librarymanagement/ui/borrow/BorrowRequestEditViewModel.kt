package com.example.librarymanagement.ui.borrow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.book.BookRepository
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import com.example.librarymanagement.data.member.MemberRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BorrowRequestEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val borrowRequestRepository: BorrowRequestRepository,
    private val borrowRepository: BorrowRepository,
    private val bookRepository: BookRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {
    var borrowUiState by mutableStateOf(BorrowUiState())
        private set

    private val borrowId: Int = checkNotNull(savedStateHandle[BorrowRequestDetailDestination.borrowRequestIdArg])

    private fun validateInput(uiState: BorrowDetail = borrowUiState.borrowDetail): Boolean {
        return with(uiState) {
            (memberName != "Mã thành viên không đúng!") && borrowDate.isNotBlank() && (countBook != 0)
                    && exceptDate.isNotBlank()
        }
//        return true
    }

    init {
        viewModelScope.launch {
            borrowUiState = borrowRequestRepository.getBorrowRequestStream(borrowId)
                .filterNotNull()
                .first()
                .toBorrowUiState()
        }
    }

    fun updateUiState(borrowDetail: BorrowDetail) {
        borrowUiState = BorrowUiState(borrowDetail = borrowDetail, isBorrowValid = validateInput(borrowDetail))
    }

//    suspend fun updateBorrow() {
//        if(validateInput(borrowUiState.borrowDetail)) {
//            borrowRepository.updateBorrow(borrowUiState.borrowDetail.toBorrow())
//        }
//    }

    fun showDialog() {
        borrowUiState = borrowUiState.copy(isShowDialog = !borrowUiState.isShowDialog)
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

    suspend fun isOutOfStock(bookId: Int): Boolean {
        if(bookId == 0) return false;
        return bookRepository.getBookStream(bookId)
            .map { (it?.quantities ?: 1) <= 0 }
            .first()
    }

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
//        val borrowRequestID = borrowRequestRepository.insertBorrowRequest(
//            borrowUiState.borrowDetail.toBorrowRequest()
//        )
//        updateUiState(borrowUiState.borrowDetail.copy(borrowId = borrowRequestID.toString().toInt()))
        borrowUiState.borrowDetail.toListBorrow().forEach { borrow ->
            borrowRepository.insertBorrow(borrow)
        }
    }
}