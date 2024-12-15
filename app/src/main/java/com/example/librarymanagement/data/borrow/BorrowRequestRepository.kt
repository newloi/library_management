package com.example.librarymanagement.data.borrow

class BorrowRequestRepository(private val borrowDao: BorrowRequestDao) {
    suspend fun insertBorrowRequest(borrowRequest: BorrowRequest) = borrowDao.insert(borrowRequest)
//
//    suspend fun updateBook(borrowRequestDetail: BorrowRequestDetail) = borrowDao.update(borrowRequestDetail)
//
//    suspend fun deleteBook(borrowRequestDetail: BorrowRequestDetail) = borrowDao.delete(borrowRequestDetail)
//
//    fun getAllBorrowRequestsStream(): Flow<List<BorrowRequestDetail>> = borrowDao.getAllBorrowRequestsDetail()
//
//    fun getBorrowRequestStream(id: Int): Flow<BorrowRequestDetail> = borrowDao.getBorrowRequestDetail(id)
}