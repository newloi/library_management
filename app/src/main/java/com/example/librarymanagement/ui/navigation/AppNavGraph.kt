package com.example.librarymanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.librarymanagement.ui.borrow.BorrowRequestDetailDestination
import com.example.librarymanagement.ui.borrow.BorrowRequestDetailScreen
import com.example.librarymanagement.ui.book.AddNewBookDestination
import com.example.librarymanagement.ui.book.AddNewBookScreen
import com.example.librarymanagement.ui.book.BookDetailDestination
import com.example.librarymanagement.ui.book.BookDetailScreen
import com.example.librarymanagement.ui.book.BooksScreen
import com.example.librarymanagement.ui.book.BooksDestination
import com.example.librarymanagement.ui.borrow.BorrowRequestsDestination
import com.example.librarymanagement.ui.borrow.BorrowRequestsScreen
import com.example.librarymanagement.ui.member.AddNewMemberDestination
import com.example.librarymanagement.ui.member.AddNewMemberScreen
import com.example.librarymanagement.ui.member.MemberDetailDestination
import com.example.librarymanagement.ui.member.MemberDetailScreen
import com.example.librarymanagement.ui.member.MembersDestination
import com.example.librarymanagement.ui.member.MembersScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BooksDestination.route,
        modifier = modifier
    ) {
        composable(route = BooksDestination.route) {
            BooksScreen(
                navigateToAddNewBook = { navController.navigate(AddNewBookDestination.route) },
                navigateToMembersScreen = { navController.navigate(MembersDestination.route) },
                navigateToBorrowRequestsScreen = { navController.navigate(BorrowRequestsDestination.route) },
                navigateToEditBook = {},
                navigateToBookDetailScreen = { navController.navigate(BookDetailDestination.route) }
            )
        }
        composable(route = AddNewBookDestination.route) {
            AddNewBookScreen(

            )
        }
        composable(route = BookDetailDestination.route) {
            BookDetailScreen(
                navigateToEditBook = { /*navController.navigate()*/ },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = BorrowRequestDetailDestination.route) {
            BorrowRequestDetailScreen(
                navigateToEditBorrowRequest = {},
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = BorrowRequestsDestination.route) {
            BorrowRequestsScreen(
                navigateToAddNewBorrowRequest = {},
                navigateToBooksScreen = { navController.navigate(BooksDestination.route) },
                navigateToMembersScreen = { navController.navigate(MembersDestination.route) }
            )
        }
        composable(route = AddNewMemberDestination.route) {
            AddNewMemberScreen(

            )
        }
        composable(route = MemberDetailDestination.route) {
            MemberDetailScreen(
                navigateToEditMember = { navController.navigate(MemberDetailDestination.route) },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = MembersDestination.route) {
            MembersScreen(
                navigateToBooksScreen = { navController.navigate(BooksDestination.route) },
                navigateToBorrowRequestsScreen = { navController.navigate(BorrowRequestsDestination.route) },
                navigateToAddNewMember = { navController.navigate(AddNewMemberDestination.route) }
                )
        }
    }
}