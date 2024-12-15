package com.example.librarymanagement.ui.borrow



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.borrow.Borrow
import com.example.librarymanagement.data.borrow.BorrowRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

//class BookDetailViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val borrowRepository: BorrowRepository
//) : ViewModel() {
//    private val borrowId: Int = checkNotNull(savedStateHandle[BorrowRequestDetailDestination.borrowIdArg])
//    val uiState: StateFlow<BorrowDetailUiState> =
//        borrowRepository.getBorrowStream
//            .filterNotNull()
//            .map {
//                BorrowDetailUiState(currentBorrow = it)
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = BorrowDetailUiState()
//            )
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
//
//    suspend fun deleteBorrow() {
//        borrowRepository.deleteBorrow(uiState.value.currentBorrow)
//    }
//}

//data class BorrowDetailUiState(
////    val currentBorrow: Borrow= Borrow("123455","123445")
//)