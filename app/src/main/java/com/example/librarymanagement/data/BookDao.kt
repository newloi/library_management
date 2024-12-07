package com.example.librarymanagement.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM books WHERE id = :id")
    fun getItem(id: Int): Flow<Book>

    @Query("SELECT * FROM books ORDER BY name ASC")
    fun getAllItems(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE name LIKE '%'||:searchText||'%' OR id = CAST(:searchText AS INTEGER)")
    fun search(searchText: String): Flow<List<Book>>
}