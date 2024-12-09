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
    }
}

fun CreationExtras.libraryManagementApplication(): LibraryManagementApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as LibraryManagementApplication)