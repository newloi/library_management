package com.example.librarymanagement.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(member: Member)

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    @Query("SELECT * FROM members WHERE id = :id")
    fun getMember(id: Int): Flow<Member>

    @Query("SELECT * FROM members ORDER BY name ASC")
    fun getAllMember(): Flow<List<Member>>

    @Query("SELECT * FROM members WHERE name LIKE '%'||:searchText||'%' OR id = CAST(:searchText AS INTEGER)")
    fun searchMembers(searchText: String): Flow<List<Member>>
}