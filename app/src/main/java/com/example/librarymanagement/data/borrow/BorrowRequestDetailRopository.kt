package com.example.librarymanagement.data.borrow

import kotlinx.coroutines.flow.Flow

class BorrowRequestDetailRepository(private val borrowRequestDetailDao: BorrowRequestDetailDao) {
    fun getAllBorrowRequestDetailStream(): Flow<List<BorrowRequestDetail>> = borrowRequestDetailDao.getAllBorrowRequestsDetail()

    fun getBorrowRequestDetailStream(borrowId: Int): Flow<BorrowRequestDetail> = borrowRequestDetailDao.getBorrowRequestDetail(borrowId)
}