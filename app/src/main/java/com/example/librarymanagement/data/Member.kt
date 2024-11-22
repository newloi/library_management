package com.example.librarymanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val gender: String,
    val dateOfBirth: String,
    val address: String,
    val registrationDate: String
)
