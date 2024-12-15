package com.example.librarymanagement.ui.borrow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.librarymanagement.data.borrow.BorrowRepository

class BorrowRequestEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val borrowRepository: BorrowRepository
) : ViewModel() {
//    var borrowUiState by mutableStateOf(BorrowUiState())
//        private set
//
//    private val borrowId: Int = checkNotNull(savedStateHandle[BorrowDetailDestination.borrowIdArg])
//
//    private fun validateInput(uiState: BorrowDetail = borrowUiState.borrowDetail): Boolean {
//        return with(uiState) {
//                    borrowId.isNotBlank() && bookId.isNotBlank()
//
//        }
//    }
//
////    init {
////        viewModelScope.launch {
////            borrowUiState = borrowRepository.getMemberStream(memberId)
////                .filterNotNull()
////                .first()
////                .toMemberUiState(true)
////        }
////    }
//
//    fun updateUiState(borrowDetail: BorrowDetail) {
//        borrowUiState = BorrowUiState(borrowDetail = borrowDetail, isBorrowValid = validateInput(borrowDetail))
//    }
//
//    suspend fun updateBorrow() {
//        if(validateInput(borrowUiState.borrowDetail)) {
//            borrowRepository.updateBorrow(borrowUiState.borrowDetail.toBorrow())
//        }
//    }
//
//    fun showDialog() {
//        borrowUiState = borrowUiState.copy(isShowDialog = !borrowUiState.isShowDialog)
//    }
}