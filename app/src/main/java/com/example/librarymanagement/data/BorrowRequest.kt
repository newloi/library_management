package com.example.librarymanagement.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "borrowRequests")
data class BorrowRequest(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val memberName: String,
    val bookName: String,
    val borrowDate: String,
    val bookCount: Int,
    val exceptDate: String,
    val returnDate: String,
    val state: String


)
