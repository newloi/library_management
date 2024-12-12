package com.example.librarymanagement.data.borrow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BorrowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(borrow: Borrow)

    @Update
    suspend fun update(borrow: Borrow)

    @Delete
    suspend fun delete(borrow: Borrow)
}