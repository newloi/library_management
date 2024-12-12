package com.example.librarymanagement.data.borrow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.librarymanagement.data.member.Member

@Entity(
    tableName = "borrowRequests",
    foreignKeys = [
        ForeignKey(
            entity = Member::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["memberId"])
    ]
)
/**
 * don muon cua tung thanh vien
 */
data class BorrowRequest(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val memberId: Int,
    val borrowDate: String,
    val bookCount: Int,
    val exceptDate: String,
    val returnDate: String,
    val state: String
)


