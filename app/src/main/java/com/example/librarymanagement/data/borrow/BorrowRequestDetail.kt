package com.example.librarymanagement.data.borrow

/**
 * thong tin chi tiet don muon
 */
data class BorrowRequestDetail(
    val borrowId : Int,
    val listBooks: String,
    val listBookIds: String,
    val memberId: Int,
    val memberName: String,
    val borrowDate: String,
    val exceptDate: String,
    val returnDate: String?,
    val state: Boolean
)
