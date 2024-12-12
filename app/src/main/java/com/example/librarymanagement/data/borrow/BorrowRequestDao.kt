package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BorrowRequestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(borrowRequest: BorrowRequest)

    @Update
    suspend fun update(borrowRequest: BorrowRequest)

    @Delete
    suspend fun delete(borrowRequest: BorrowRequest)
}