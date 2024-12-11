package com.example.librarymanagement.ui.book

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.data.BookRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BooksScreenViewModel(private val bookRepository: BookRepository) : ViewModel() {

    // StateFlow chứa trạng thái của UI
    private val _booksScreenUiState = MutableStateFlow(BooksScreenUiState())
    val booksScreenUiState: StateFlow<BooksScreenUiState> = _booksScreenUiState

    private var currentSearchJob: Job? = null // Để quản lý tìm kiếm
//    private var isSortIncreasing: Boolean = true


//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }

    init {
        // Mặc định hiển thị toàn bộ sách
        loadAllBooks()
    }

    private fun loadAllBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooksStream()
                .map { books ->
                    val currentBooks = books
                    BooksScreenUiState(
                        books = if(_booksScreenUiState.value.isSortIncreasing) {
                                    currentBooks.sortedBy { it.name }
                                } else {
                                    currentBooks.sortedByDescending { it.name }
                                },
                        isSortIncreasing = _booksScreenUiState.value.isSortIncreasing,
                        sortBy = _booksScreenUiState.value.sortBy
                    )
                }
                .collect {
                    _booksScreenUiState.value = it
                }
        }
    }

    fun searchBooks(searchText: String) {
        currentSearchJob?.cancel() // Hủy job tìm kiếm trước đó (nếu có)
        currentSearchJob = viewModelScope.launch {
            if (searchText.isBlank()) {
                // Nếu không nhập từ khóa, hiển thị toàn bộ sách
                loadAllBooks()
            } else {
                bookRepository.searchBooksStream(searchText)
                    .map {books ->
                        val currentBooks = books
                        BooksScreenUiState(
                            books = if(_booksScreenUiState.value.isSortIncreasing) {
                                        currentBooks.sortedBy { it.name }
                                    } else {
                                        currentBooks.sortedByDescending { it.name }
                                    },
                            isSortIncreasing = _booksScreenUiState.value.isSortIncreasing,
                            sortBy = _booksScreenUiState.value.sortBy
                        )
                    }
                    .collect {
                        _booksScreenUiState.value = it
                    }
            }
        }
    }

    fun toggleSortOrder() {
//        isSortIncreasing = !isSortIncreasing
//        loadAllBooks()
        _booksScreenUiState.update { currentState ->
            currentState.copy(
                isSortIncreasing = !currentState.isSortIncreasing
            )
        }
    }

    fun setSortBy(sortBy: Int) {
        _booksScreenUiState.update { currentState ->
            currentState.copy(
                sortBy = sortBy
            )
        }
    }

    suspend fun deleteBook(book: Book) {
        bookRepository.deleteBook(book)
        // Làm mới danh sách sau khi xóa
        searchBooks("") // Hiển thị lại toàn bộ sách
    }
}


data class BooksScreenUiState(
    val books: List<Book> = listOf(),
    val isSortIncreasing: Boolean = true,
    val sortBy: Int = 0
)