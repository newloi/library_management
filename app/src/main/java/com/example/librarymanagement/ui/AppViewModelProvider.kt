package com.example.librarymanagement.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.librarymanagement.LibraryManagementApplication
import com.example.librarymanagement.ui.book.AddNewBookViewModel
import com.example.librarymanagement.ui.book.BookDetailViewModel
import com.example.librarymanagement.ui.book.BookEditViewModel
import com.example.librarymanagement.ui.book.BooksScreenViewModel
import com.example.librarymanagement.ui.borrow.AddNewBorrowRequestViewModel
import com.example.librarymanagement.ui.borrow.BorrowRequestDetailViewModel
import com.example.librarymanagement.ui.borrow.BorrowRequestEditViewModel
import com.example.librarymanagement.ui.borrow.BorrowRequestsViewModel
import com.example.librarymanagement.ui.member.AddNewMemberViewModel
import com.example.librarymanagement.ui.member.MemberDetailViewModel
import com.example.librarymanagement.ui.member.MemberEditViewModel
import com.example.librarymanagement.ui.member.MembersScreenViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BooksScreenViewModel( libraryManagementApplication().container.bookRepository )
        }
        initializer {
            AddNewBookViewModel( libraryManagementApplication().container.bookRepository )
        }
        initializer {
            BookDetailViewModel(
                this.createSavedStateHandle(),
                libraryManagementApplication().container.bookRepository
            )
        }
        initializer {
            BookEditViewModel(
                this.createSavedStateHandle(),
                libraryManagementApplication().container.bookRepository
            )
        }
        initializer {
            AddNewMemberViewModel( libraryManagementApplication().container.memberRepository )
        }
        initializer {
            MemberDetailViewModel(
                this.createSavedStateHandle(),
                libraryManagementApplication().container.memberRepository
            )
        }
        initializer {
            MemberEditViewModel(
                this.createSavedStateHandle(),
                libraryManagementApplication().container.memberRepository
            )
        }
        initializer {
            MembersScreenViewModel( libraryManagementApplication().container.memberRepository )
        }
        initializer {
            AddNewBorrowRequestViewModel(
                borrowRepository = libraryManagementApplication().container.borrowRepository,
                borrowRequestRepository = libraryManagementApplication().container.borrowRequestRepository,
                bookRepository = libraryManagementApplication().container.bookRepository,
                memberRepository = libraryManagementApplication().container.memberRepository
            )
        }
        initializer {
            BorrowRequestsViewModel(
                borrowRequestRepository = libraryManagementApplication().container.borrowRequestRepository,
                memberRepository = libraryManagementApplication().container.memberRepository,
                bookRepository = libraryManagementApplication().container.bookRepository,
                borrowRepository = libraryManagementApplication().container.borrowRepository
            )
        }
        initializer {
            BorrowRequestDetailViewModel(
                this.createSavedStateHandle(),
                borrowRepository = libraryManagementApplication().container.borrowRepository,
                bookRepository = libraryManagementApplication().container.bookRepository,
                memberRepository = libraryManagementApplication().container.memberRepository,
                borrowRequestRepository = libraryManagementApplication().container.borrowRequestRepository
            )
        }
        initializer {
            BorrowRequestEditViewModel(
                this.createSavedStateHandle(),
                borrowRepository = libraryManagementApplication().container.borrowRepository,
                borrowRequestRepository = libraryManagementApplication().container.borrowRequestRepository,
                bookRepository = libraryManagementApplication().container.bookRepository,
                memberRepository = libraryManagementApplication().container.memberRepository
            )
        }
    }
}

fun CreationExtras.libraryManagementApplication(): LibraryManagementApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as LibraryManagementApplication)