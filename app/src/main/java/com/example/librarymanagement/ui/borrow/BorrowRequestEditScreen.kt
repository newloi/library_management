package com.example.librarymanagement.ui.borrow

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmCancel
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import kotlinx.coroutines.launch

object BorrowRequestEditDestination : NavigationDestination {
    override val route: String = "borrow_request_edit"
    const val borrowRequestIdArg = "borrowRequestId"
    val routeWithArgs = "${route}/{$borrowRequestIdArg}"
}

@Composable
fun BorrowRequestEditScreen(
    navigateBack: () -> Unit,
    viewModel: BorrowRequestEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddAppBar(
                title = stringResource(R.string.don_muon, viewModel.borrowUiState.borrowDetail.borrowId),
                navigateBack = viewModel::showDialog
            )
        }
//        floatingActionButton = {
//            Button(
//                onClick = {},
//                shape = RoundedCornerShape(16.dp),
//                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
//                modifier = Modifier.size(100.dp, 40.dp)
//            ) {
//                Text(
//                    text = "Xong",
//                    style = MaterialTheme.typography.labelMedium
//                )
//            }
//        }
    ) { innerPadding ->
        AddNewBorrowRequest(
            onBorrowChange = viewModel::updateUiState,
            borrowDetail = viewModel.borrowUiState.borrowDetail,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveBorrow()
                    navigateBack()
                }
            },
            getBookName = { bookId ->
                viewModel.getBookName(bookId)
            },
            getMemberName = { memberId ->
                viewModel.getMemberName(memberId)
            },
//            getMemberName = { it -> ""},
            isOutOfStock = { bookId ->
                viewModel.isOutOfStock(bookId)
            },
            enable = viewModel.borrowUiState.isBorrowValid,
            modifier = Modifier.padding(innerPadding)
        )
        if(viewModel.borrowUiState.isShowDialog) {
            ConfirmCancel(
                onDelete = {
                    viewModel.showDialog()
                    navigateBack()
                },
                onCancel = viewModel::showDialog
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun AddNewBorrowScreenPreview() {
//    LibraryManagementTheme { BorrowRequestEditScreen(
//        navigateBack={},
//       )
//    }
//}
//
//
