package com.example.librarymanagement.ui.borrow


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.data.book.BookRepository
import com.example.librarymanagement.data.borrow.Borrow
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.data.borrow.BorrowRequestDetailRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import com.example.librarymanagement.data.member.MemberRepository
import com.example.librarymanagement.ui.book.BookDetailUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BorrowRequestDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val borrowRequestRepository: BorrowRequestRepository,
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository,
    private val borrowRepository: BorrowRepository
) : ViewModel() {
    private val borrowId: Int =
        checkNotNull(savedStateHandle[BorrowRequestDetailDestination.borrowRequestIdArg])
    val uiState: StateFlow<BorrowRequestUiState> =
        combine(
            borrowRequestRepository.getBorrowRequestStream(borrowId).filterNotNull(),
            borrowRepository.getAllBookIdsStreamWith(borrowId)
        ) { borrowRequest, listBooks ->
            BorrowRequestUiState(
                currentBorrowRequest = borrowRequest,
                listBooks = listBooks
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BorrowRequestUiState()
            )

    //
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    //
    suspend fun deleteBorrowRequest() {
        borrowRequestRepository.deleteBorrowRequest(uiState.value.currentBorrowRequest)
    }

    suspend fun getMemberName(memberId: Int): String {
        return memberRepository.getMemberStream(memberId)
            .map { it?.name ?: "" }
            .first()
    }

    suspend fun getBookName(bookId: Int): String {
        return bookRepository.getBookStream(bookId)
            .map { it?.name ?: "" }
            .first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markReturned(borrowRequestId: Int) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        viewModelScope.launch {
            val borrowRequest =
                borrowRequestRepository.getBorrowRequestStream(borrowRequestId).first()
            borrowRequestRepository.updateBorrowRequest(
                borrowRequest.copy(
                    returnDate = currentDate.format(formatter),
                    state = true
                )
            )
        }
    }
}

data class BorrowRequestUiState(
    val currentBorrowRequest: BorrowRequest = BorrowRequest(
        id = 0,
        memberId = 0,
//        memberName = "",
        bookCount = 0,
        borrowDate = "",
        exceptDate = "",
        returnDate = "",
        state = false,
    ),
    val listBooks: List<Int> = listOf()
)