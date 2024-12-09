package com.example.librarymanagement.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllBooks()

    fun getBookStream(id: Int): Flow<Book> = bookDao.getBook(id)

    fun getAllBooksWithSearchText(searchText: String): Flow<List<Book>> = bookDao.search(searchText)
}