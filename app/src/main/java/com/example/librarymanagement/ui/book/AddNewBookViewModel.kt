package com.example.librarymanagement.ui.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.data.BookRepository

class AddNewBookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    fun updateUiState(bookDetail: BookDetail) {
        bookUiState = BookUiState(bookDetail = bookDetail, isBookValid = validatInput(bookDetail))
    }

    private fun validatInput(uiState: BookDetail = bookUiState.bookDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && author.isNotBlank() &&
                    publisher.isNotBlank() && year.toString().isNotBlank() &&
                    type.isNotBlank() && quantities.toString().isNotBlank()
        }
    }

    suspend fun saveItem(){
        if(validatInput()) {
            bookRepository.insertBook(bookUiState.bookDetail.toBook())
        }
    }
}

data class BookDetail(
    val id: Int = 0,
    val name:String = "",
    val author: String = "",
    val publisher: String = "",
    val year: String = "",
    val type: String = "",
    val quantities: String = ""
)

data class BookUiState(
    val isBookValid: Boolean = false,
    val bookDetail: BookDetail = BookDetail()
)

fun BookDetail.toBook():Book = Book(
    id = id,
    name = name,
    author = author,
    publisher = publisher,
    year = year.toIntOrNull() ?: 0,
    type = type,
    quantities = quantities.toIntOrNull() ?: 0
)

fun Book.toBookDetail(): BookDetail = BookDetail(
    id = id,
    name = name,
    author = author,
    publisher = publisher,
    year = year.toString(),
    type = type,
    quantities = quantities.toString()
)

fun Book.toBookUiState(isBookValid: Boolean = false): BookUiState = BookUiState(
    bookDetail = this.toBookDetail(),
    isBookValid =isBookValid
)