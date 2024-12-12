package com.example.librarymanagement.data.book

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(book: Book) = bookDao.delete(book)

    fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllBooks()

    fun getBookStream(id: Int): Flow<Book> = bookDao.getBook(id)

    fun searchBooksStream(searchText: String): Flow<List<Book>> = bookDao.searchBooks(searchText)
}