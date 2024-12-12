package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowRequestDetailDao {
    @Query("""
        SELECT 
            borrowRequests.id AS borrowId,
            books.name AS bookName,
            members.name AS memberName,
            bookCount,
            exceptDate,
            borrowRequests.borrowDate AS borrowDate,
            borrowRequests.returnDate AS returnDate,
            state
        FROM borrows
        LEFT JOIN books ON books.id = borrows.bookId
        LEFT JOIN borrowRequests ON borrowRequests.id = borrows.borrowId
        LEFT JOIN members ON members.id = borrowRequests.memberId
    """)
    fun getAllBorrowRequestsDetail(): Flow<List<BorrowRequestDetail>>
}