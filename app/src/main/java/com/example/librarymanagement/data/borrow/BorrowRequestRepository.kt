package com.example.librarymanagement.data.borrow

import kotlinx.coroutines.flow.Flow

class BorrowRequestRepository(private val borrowRequestDao: BorrowRequestDao) {
    suspend fun insertBorrowRequest(borrowRequest: BorrowRequest) = borrowRequestDao.insert(borrowRequest)
//
    suspend fun updateBorrowRequest(borrowRequest: BorrowRequest) = borrowRequestDao.update(borrowRequest)
//
    suspend fun deleteBorrowRequest(borrowRequest: BorrowRequest) = borrowRequestDao.delete(borrowRequest)
//
    fun getAllBorrowRequestsStream(): Flow<List<BorrowRequest>> = borrowRequestDao.getAllBorrowRequests()
//
    fun getBorrowRequestStream(id: Int): Flow<BorrowRequest> = borrowRequestDao.getBorrowRequest(id)

    fun searchBorrowRequestsStream(searchText: String): Flow<List<BorrowRequest>> = borrowRequestDao.searchBorrowRequests(searchText)

    fun searchByDate(day: String, month: String, year: String): Flow<List<BorrowRequest>> = borrowRequestDao.searchByDate(day, month, year)
}