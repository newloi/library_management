package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface BorrowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(borrow: Borrow)

    @Update
    suspend fun update(borrow: Borrow)

    @Delete
    suspend fun delete(borrow: Borrow)

    @Query("""
        SELECT GROUP_CONCAT(bookId, ',')
        FROM borrows
        WHERE borrowId = :borrowId
    """)
    fun getAllBookIdsWith(borrowId: Int): Flow<String>

    fun getBookIdList(borrowId: Int): Flow<List<Int>> {
        return getAllBookIdsWith(borrowId).map { bookIds ->
            bookIds.split(",").mapNotNull { it.toIntOrNull() }
        }
    }
}