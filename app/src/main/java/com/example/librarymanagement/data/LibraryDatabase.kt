package com.example.librarymanagement.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Book::class, Member::class], version = 1, exportSchema = false)
abstract class LibraryDatabase : RoomDatabase() {
}