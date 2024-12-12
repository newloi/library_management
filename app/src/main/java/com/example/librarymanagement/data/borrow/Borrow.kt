package com.example.librarymanagement.data.borrow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    ],
    indices = [
        Index(value = ["bookId"]),
        Index(value = ["borrowId"])
    ]
)
/**
 * quan he don muon - sach
 */
data class Borrow(
    val borrowId: Int,
    val bookId: Int
)