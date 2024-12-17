package com.example.librarymanagement.ui.borrow

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.data.book.BookRepository
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetailRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import com.example.librarymanagement.data.member.MemberRepository
import com.example.librarymanagement.ui.book.BooksScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BorrowRequestsViewModel(
    private val borrowRepository: BorrowRepository,
    private val borrowRequestRepository: BorrowRequestRepository,
    private val memberRepository: MemberRepository,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _borroRequestsUiState = MutableStateFlow(BorrowRequestsUiState())
    val borrowRequestsUiState: StateFlow<BorrowRequestsUiState> = _borroRequestsUiState

    private var currentSearchJob: Job? = null

    init {
        // Mặc định hiển thị toàn bộ sách
        loadAllBorrowRequests()
    }

    private fun loadAllBorrowRequests() {
        viewModelScope.launch {
            borrowRequestRepository.getAllBorrowRequestsStream()
                .map { borrowRequests ->
//                    val currentBorrowRequests = borrowRequests
                    BorrowRequestsUiState(
//                        books = if(_booksScreenUiState.value.isSortIncreasing) {
//                            currentBooks.sortedBy { it.name }
//                        } else {
//                            currentBooks.sortedByDescending { it.name }
//                        },
//                        isSortIncreasing = _booksScreenUiState.value.isSortIncreasing,
//                        sortBy = _booksScreenUiState.value.sortBy
                        borrowRequests = borrowRequests,
                        onReturned = _borroRequestsUiState.value.onReturned
                    )
                }
                .collect {
                    _borroRequestsUiState.value = it
                }
        }
    }

    fun searchBorrowRequests(searchText: String) {
        currentSearchJob?.cancel() // Hủy job tìm kiếm trước đó (nếu có)
        currentSearchJob = viewModelScope.launch {
            if (searchText.isBlank()) {
                // Nếu không nhập từ khóa, hiển thị toàn bộ sách
                loadAllBorrowRequests()
            } else {
                borrowRequestRepository.searchBorrowRequestsStream(searchText)
                    .map { borrowRequests ->
//                        val currentBooks = books
                        BorrowRequestsUiState(
//                            books = if(_booksScreenUiState.value.isSortIncreasing) {
//                                currentBooks.sortedBy { it.name }
//                            } else {
//                                currentBooks.sortedByDescending { it.name }
//                            },
//                            isSortIncreasing = _booksScreenUiState.value.isSortIncreasing,
//                            sortBy = _booksScreenUiState.value.sortBy
                            borrowRequests = borrowRequests,
                            onReturned = _borroRequestsUiState.value.onReturned
                        )
                    }
                    .collect {
                        _borroRequestsUiState.value = it
                    }
            }
        }
    }

    fun searchByDate(day: String, month: String, year: String) {
        currentSearchJob?.cancel() // Hủy job tìm kiếm trước đó (nếu có)
        currentSearchJob = viewModelScope.launch {
            if (day.isBlank() && month.isBlank() && year.isBlank()) {
                // Nếu không nhập từ khóa, hiển thị toàn bộ sách
                loadAllBorrowRequests()
            } else {
                borrowRequestRepository.searchByDate(day, month, year)
                    .map { borrowRequests ->
//                        val currentBooks = books
                        BorrowRequestsUiState(
//                            books = if(_booksScreenUiState.value.isSortIncreasing) {
//                                currentBooks.sortedBy { it.name }
//                            } else {
//                                currentBooks.sortedByDescending { it.name }
//                            },
//                            isSortIncreasing = _booksScreenUiState.value.isSortIncreasing,
//                            sortBy = _booksScreenUiState.value.sortBy
                            borrowRequests = borrowRequests,
                            onReturned = _borroRequestsUiState.value.onReturned
                        )
                    }
                    .collect {
                        _borroRequestsUiState.value = it
                    }
            }
        }
    }

    fun switchStateTab() {
//        isSortIncreasing = !isSortIncreasing
//        loadAllBooks()
        _borroRequestsUiState.update { currentState ->
            currentState.copy(
                onReturned = !currentState.onReturned
            )
        }
    }

//    fun setSortBy(sortBy: Int) {
//        _borroRequestsUiState.update { currentState ->
//            currentState.copy(
//                sortBy = sortBy
//            )
//        }
//    }

    suspend fun getMemberName(memberId: Int): String {
        return memberRepository.getMemberStream(memberId)
            .map { it?.name ?: "" }
            .first()
    }

    suspend fun deleteBorrowRequest(borrowRequest: BorrowRequest) {
        borrowRequestRepository.deleteBorrowRequest(borrowRequest)
        // Làm mới danh sách sau khi xóa
//        searchBorrowRequests("") // Hiển thị lại toàn bộ sách
        loadAllBorrowRequests()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markReturned(borrowRequestId: Int) {
        addQuantityByOne(borrowRequestId)
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

    private fun addQuantityByOne(borrowId: Int) {
        viewModelScope.launch {
            borrowRepository.getAllBookIdsStreamWith(borrowId).collect { bookList ->
                bookList.forEach { book ->
                    // Lấy thông tin sách hiện tại từ bookRepository
                    val currentBook = bookRepository.getBookStream(book).first()
                    if (currentBook != null) {
                        // Tăng số lượng lên 1 và cập nhật lại trong cơ sở dữ liệu
                        bookRepository.updateBook(currentBook.copy(quantities = currentBook.quantities + 1))
                    }
                }
            }
        }
    }

//    fun updateDateSearch(day: String, month: String, year: String) {
//        _borroRequestsUiState.update { currentState ->
//            currentState.copy(
//                day = day, month = month, year = year
//            )
//        }
//    }
}

data class BorrowRequestsUiState(
    val borrowRequests: List<BorrowRequest> = listOf(),
    val onReturned: Boolean = false,
//    val day: String = "",
//    val month: String = "",
//    val year: String = ""
)