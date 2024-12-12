package com.example.librarymanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.data.book.BookDao
import com.example.librarymanagement.data.borrow.Borrow
import com.example.librarymanagement.data.borrow.BorrowDao
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDao
import com.example.librarymanagement.data.borrow.BorrowRequestDetailDao
import com.example.librarymanagement.data.member.Member
import com.example.librarymanagement.data.member.MemberDao

@Database(entities = [Book::class, Member::class, Borrow::class, BorrowRequest::class], version = 2, exportSchema = false)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun memberDao(): MemberDao
    abstract fun borrowDao(): BorrowDao
    abstract fun borrowRequestDao(): BorrowRequestDao
    abstract fun borrowRequestDetailDao(): BorrowRequestDetailDao

    companion object {
        @Volatile
        private var Instance: LibraryDatabase? = null

        fun getDatabase(context: Context): LibraryDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, LibraryDatabase::class.java, "library_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}