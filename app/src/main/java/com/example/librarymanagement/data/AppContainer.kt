package com.example.librarymanagement.data

import android.content.Context
import com.example.librarymanagement.data.book.BookRepository
import com.example.librarymanagement.data.borrow.BorrowRepository
import com.example.librarymanagement.data.borrow.BorrowRequestDetailRepository
import com.example.librarymanagement.data.borrow.BorrowRequestRepository
import com.example.librarymanagement.data.member.MemberRepository

interface AppContainer {
    val bookRepository: BookRepository
    val memberRepository: MemberRepository
    val borrowRepository: BorrowRepository
    val borrowRequestRepository: BorrowRequestRepository
    val borrowRequestDetailRepository: BorrowRequestDetailRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val bookRepository: BookRepository by lazy {
        BookRepository(LibraryDatabase.getDatabase(context).bookDao())
    }
    override val memberRepository: MemberRepository by lazy {
        MemberRepository(LibraryDatabase.getDatabase(context).memberDao())
    }
    override val borrowRepository: BorrowRepository by lazy {
        BorrowRepository(LibraryDatabase.getDatabase(context).borrowDao())
    }
    override val borrowRequestRepository: BorrowRequestRepository by lazy {
        BorrowRequestRepository(LibraryDatabase.getDatabase(context).borrowRequestDao())
    }
    override val borrowRequestDetailRepository: BorrowRequestDetailRepository by lazy {
        BorrowRequestDetailRepository(LibraryDatabase.getDatabase(context).borrowRequestDetailDao())
    }
}