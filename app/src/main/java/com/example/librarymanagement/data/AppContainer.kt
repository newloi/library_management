package com.example.librarymanagement.data

import android.content.Context

interface AppContainer {
    val bookRepository: BookRepository
    val memberRepository: MemberRepository
    val borrowRequestRepository: BorrowRequestRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val bookRepository: BookRepository by lazy {
        BookRepository(LibraryDatabase.getDatabase(context).bookDao())
    }
    override val memberRepository: MemberRepository by lazy {
        MemberRepository(LibraryDatabase.getDatabase(context).memberDao())
    }
    override val borrowRequestRepository: BorrowRequestRepository by lazy {
        BorrowRequestRepository(LibraryDatabase.getDatabase(context).borrowDao())
    }
}