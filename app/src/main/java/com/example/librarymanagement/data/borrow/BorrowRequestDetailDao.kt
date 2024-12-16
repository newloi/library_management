package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface BorrowRequestDetailDao {
    @Query("""
        SELECT 
            borrows.borrowId AS borrowId,
            GROUP_CONCAT(books.name, ',') AS listBooks, 
            GROUP_CONCAT(books.id, ',') AS listBookIds,
            members.id AS memberId,
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
        GROUP BY borrowRequests.id, members.name, memberId, bookCount, exceptDate, borrowRequests.borrowDate, borrowRequests.returnDate, state
    """)
    fun getAllBorrowRequestsDetail(): Flow<List<BorrowRequestDetail>>

    // Hàm chuyển đổi từ String thành List<String>
//    @Transaction
//    suspend fun getAllBorrowRequestsDetail(): Flow<List<BorrowRequestDetail>> {
//        return getAllBorrowRequests().map {
//            it.map { borrowRequest ->
//                borrowRequest.copy(
//                    listBooks = borrowRequest.listBooks[0].split(","), // Tách chuỗi thành List<String>
//                    listBookIds = borrowRequest.listBookIds[0].split(",")
//                )
//            }
//        }
//    }
    @Query("""
        SELECT 
            borrows.borrowId AS borrowId,
            --GROUP_CONCAT(books.name, ',') AS listBooks, 
            GROUP_CONCAT(books.id, ',') AS listBookIds,
            members.id AS memberId,
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
        --GROUP BY borrowRequests.id, members.name, memberId, bookCount, exceptDate, borrowRequests.borrowDate, borrowRequests.returnDate, state
        WHERE borrowId = :borrowId
    """)
    fun getBorrowRequestDetail(borrowId: Int): Flow<BorrowRequestDetail>
}
