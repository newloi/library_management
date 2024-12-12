package com.example.librarymanagement.data.borrow

/**
 * thong tin chi tiet don muon
 */
data class BorrowRequestDetail(
    val borrowId : Int,
    val bookName: String,
    val memberName: String,
    val borrowDate: String,
    val bookCount: Int,
    val exceptDate: String,
    val returnDate: String,
    val state: String
)
