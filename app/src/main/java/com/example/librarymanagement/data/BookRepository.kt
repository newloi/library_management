package com.example.librarymanagement.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    fun getAllBooksStream(): Flow<List<Book>> = bookDao.getAllItems()

    fun getAllBooksWithSearchText(searchText: String): Flow<List<Book>> = bookDao.search(searchText)
}