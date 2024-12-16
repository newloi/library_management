package com.example.librarymanagement.data.book

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(book: Book) {
        book.imageUri.let { uriString ->
            val uri = Uri.parse(uriString)
            if (uri.scheme == "file") { // Kiểm tra xem URI có trỏ tới file không
                val file = File(uri.path ?: "")
                if (file.exists()) {
                    file.delete() // Xóa file
                }
            }
        }
        bookDao.delete(book)
    }

    fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllBooks()

    fun getBookStream(id: Int): Flow<Book?> = bookDao.getBook(id)

    fun searchBooksStream(searchText: String): Flow<List<Book>> = bookDao.searchBooks(searchText)
}