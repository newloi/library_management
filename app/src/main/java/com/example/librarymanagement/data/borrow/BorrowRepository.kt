package com.example.librarymanagement.data.borrow

import com.example.librarymanagement.data.book.Book
import kotlinx.coroutines.flow.Flow

class BorrowRepository(private val borrowDao: BorrowDao) {
    suspend fun insertBorrow(borrow: Borrow) = borrowDao.insert(borrow)

    suspend fun updateBorrow(borrow: Borrow) = borrowDao.update(borrow)

    suspend fun deleteBorrow(borrow: Borrow) = borrowDao.delete(borrow)

}