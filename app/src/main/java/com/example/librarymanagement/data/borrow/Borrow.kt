package com.example.librarymanagement.data.borrow

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.librarymanagement.data.book.Book


@Entity(
    tableName = "borrows",
    primaryKeys = ["borrowId", "bookId"],
    foreignKeys = [
        ForeignKey(
            entity = BorrowRequest::class,
            parentColumns = ["id"],
            childColumns = ["borrowId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
/**
 * quan he don muon - sach
 */
data class Borrow(
    val borrowId: Int,
    val bookId: Int
)