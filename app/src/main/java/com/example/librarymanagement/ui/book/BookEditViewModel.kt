package com.example.librarymanagement.ui.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.BookRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailDestination.bookIdArg])

    private fun validatInput(uiState: BookDetail = bookUiState.bookDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && author.isNotBlank() &&
                    publisher.isNotBlank() && year.isNotBlank() &&
                    type.isNotBlank() && quantities.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            bookUiState = bookRepository.getBookStream(bookId)
                .filterNotNull()
                .first()
                .toBookUiState(true)
        }
    }

    fun updateUiState(bookDetail: BookDetail) {
        bookUiState = BookUiState(bookDetail = bookDetail, isBookValid = validatInput(bookDetail))
    }

    suspend fun updateBook() {
        if(validatInput(bookUiState.bookDetail)) {
            bookRepository.updateBook(bookUiState.bookDetail.toBook())
        }
    }

    fun showDialog() {
        bookUiState = bookUiState.copy(isShowDialog = !bookUiState.isShowDialog)
    }
}