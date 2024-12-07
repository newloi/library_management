package com.example.librarymanagement.ui.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BooksScreenViewModel( bookRepository: BookRepository ) : ViewModel() {
    val _booksScreenUiState = MutableStateFlow(BooksScreenUiState())
    val booksScreenUiState: StateFlow<BooksScreenUiState> =
        bookRepository.getAllBooksStream().map { BooksScreenUiState(books = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BooksScreenUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun search(searchText: String) {
//        viewModelScope.launch {
//            booksScreenUiState =
//            bookRepository.getAllBooksStream().map { BooksScreenUiState(books = it) }
//        }
    }
}

data class BooksScreenUiState(
    val books: List<Book> = listOf(),
    val isSortIncreasing: Boolean = true
)