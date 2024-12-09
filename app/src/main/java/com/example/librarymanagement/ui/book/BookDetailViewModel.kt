package com.example.librarymanagement.ui.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.data.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailDestination.bookIdArg])
    val uiState: StateFlow<BookDetailUiState> =
        bookRepository.getBookStream(bookId)
            .filterNotNull()
            .map {
                BookDetailUiState(currentBook = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookDetailUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteBook() {
        bookRepository.deleteBook(uiState.value.currentBook)
    }
}

data class BookDetailUiState(
    val currentBook: Book = Book(
        id = 0,
        name = "",
        author = "",
        publisher = "",
        year = 0,
        type = "",
        quantities = 0,
    )
)