package com.example.librarymanagement.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.librarymanagement.ui.HomeBottomAppBar
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

//@SuppressLint("RestrictedApi")
@Composable
fun AppNavHost(
    navController: NavHostController,
//    viewModel: HomeScreenViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
////    val currentBackStackEntry by navController.currentBackStackEntryAsState()
////    val currentRoute = currentBackStackEntry?.destination?.route
//    val uiState by viewModel.uiState.collectAsState()

//    Scaffold(
//        bottomBar = {
//            HomeBottomAppBar(navController = navController)
//        }
//    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BooksDestination.route,
//            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = BooksDestination.route) {
                BooksScreen(
                    navigateToAddNewBook = { navController.navigate(AddNewBookDestination.route) },
                    navigateToMembersScreen = { navController.navigate(MembersDestination.route) },
                    navigateToBorrowRequestsScreen = { navController.navigate(BorrowRequestsDestination.route) },
                    navigateToSettingScreen = {},
                    navigateToEditBook = {},
                    navigateToBookDetailScreen = { navController.navigate(BookDetailDestination.route) },
                )
            }
            composable(route = AddNewBookDestination.route) {
                AddNewBookScreen(
                    navigateBack = { navController.navigateUp() }
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
                    navigateToMembersScreen = { navController.navigate(MembersDestination.route) },
                    navigateToSettingScreen = {}
                )
            }
            composable(route = AddNewMemberDestination.route) {
                AddNewMemberScreen(
                    navigateBack = { navController.navigateUp() }
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
                    navigateToAddNewMember = { navController.navigate(AddNewMemberDestination.route) },
                    navigateToSettingScreen = {},
                    navigateToMemberDetail = { navController.navigate(MemberDetailDestination.route) }
                )
            }
        }
//    }

}