package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowRequestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(borrowRequest: BorrowRequest): Long

    @Update
    suspend fun update(borrowRequest: BorrowRequest)

    @Delete
    suspend fun delete(borrowRequest: BorrowRequest)

    @Query("SELECT * FROM borrowRequests WHERE id = :id")
    fun getBorrowRequest(id: Int): Flow<BorrowRequest>

    @Query("SELECT * FROM borrowRequests ORDER BY id ASC")
    fun getAllBorrowRequests(): Flow<List<BorrowRequest>>

    @Query("""
        SELECT borrowRequests.*
        FROM borrowRequests
        LEFT JOIN members ON members.id = borrowRequests.memberId
        WHERE members.name LIKE '%'||:searchText||'%' 
        OR borrowRequests.id = CAST(:searchText AS INTEGER)
        OR memberId = CAST(:searchText AS INTEGER)
    """)
    fun searchBorrowRequests(searchText: String): Flow<List<BorrowRequest>>

    @Query("""
        SELECT * 
        FROM borrowRequests
        WHERE 
            (:day = '' OR SUBSTR(borrowDate, 1, 2) = :day)
            AND (:month = '' OR SUBSTR(borrowDate, 4, 2) = :month)
            AND (:year = '' OR SUBSTR(borrowDate, 7, 4) = :year)
    """)
    fun searchByDate(day: String, month: String, year: String): Flow<List<BorrowRequest>>
}