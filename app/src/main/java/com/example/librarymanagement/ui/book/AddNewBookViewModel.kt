package com.example.librarymanagement.ui.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.data.book.BookRepository

class AddNewBookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    fun updateUiState(bookDetail: BookDetail) {
        bookUiState = BookUiState(bookDetail = bookDetail, isBookValid = validateInput(bookDetail))
    }

    private fun validateInput(uiState: BookDetail = bookUiState.bookDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && author.isNotBlank() &&
                    publisher.isNotBlank() && year.isNotBlank() &&
                    type.isNotBlank() && quantities.isNotBlank()
//                    && quantities.toInt() >= 0
        }
    }

    suspend fun saveBook(){
        if(validateInput()) {
            bookRepository.insertBook(bookUiState.bookDetail.toBook())
        }
    }

    fun showDialog() {
        bookUiState = bookUiState.copy(isShowDialog = !bookUiState.isShowDialog)
    }
}

data class BookDetail(
    val id: Int = 0,
    val name:String = "",
    val author: String = "",
    val publisher: String = "",
    val year: String = "",
    val type: String = "",
    val quantities: String = "",
    val imageUri: String = ""
)

data class BookUiState(
    val isBookValid: Boolean = false,
    val bookDetail: BookDetail = BookDetail(),
    val isShowDialog: Boolean = false
)

fun BookDetail.toBook(): Book = Book(
    id = id,
    name = name,
    author = author,
    publisher = publisher,
    year = year.toIntOrNull() ?: 0,
    type = type,
    quantities = quantities.toIntOrNull() ?: 0,
    imageUri = imageUri
)

fun Book.toBookDetail(): BookDetail = BookDetail(
    id = id,
    name = name,
    author = author,
    publisher = publisher,
    year = year.toString(),
    type = type,
    quantities = quantities.toString(),
    imageUri = imageUri
)

fun Book.toBookUiState(isBookValid: Boolean = false): BookUiState = BookUiState(
    bookDetail = this.toBookDetail(),
    isBookValid = isBookValid
)