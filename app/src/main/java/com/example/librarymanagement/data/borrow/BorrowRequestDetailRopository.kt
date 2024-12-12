package com.example.librarymanagement.data.borrow

import kotlinx.coroutines.flow.Flow

class BorrowRequestDetailRepository(private val borrowRequestDetailDao: BorrowRequestDetailDao) {
    fun getAllBorrowRequestDetail(): Flow<List<BorrowRequestDetail>> = borrowRequestDetailDao.getAllBorrowRequestsDetail()
}