package com.example.librarymanagement.data.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val author: String,
    val publisher: String,
    val year: Int,
    val type: String,
    val quantities: Int,
    val imageUri: String
)