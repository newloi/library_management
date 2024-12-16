package com.example.librarymanagement.ui.borrow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.ui.AddButton
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.BorrowStateBottomBar
import com.example.librarymanagement.ui.ConfirmDelete
import com.example.librarymanagement.ui.FilterByDateBar
import com.example.librarymanagement.ui.HomeBottomAppBar
import com.example.librarymanagement.ui.SearchTopBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Title
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.Locale

object BorrowRequestsDestination : NavigationDestination {
    override val route = "borrow_requests"
}

@Composable
fun BorrowRequestsScreen(
    navigateToAddNewBorrowRequest: () -> Unit,
    navigateToBooksScreen: () -> Unit,
    navigateToMembersScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToEditBorrowRequest: (Int) -> Unit,
    navigateToBorrowRequestDetail: (Int) -> Unit,
    viewModel: BorrowRequestsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.borrowRequestsUiState.collectAsState()
//    val vietnameseCollator = Collator.getInstance(Locale("vi", "VN"))
    val borrowRequests =
        if(uiState.onReturned) {
            uiState.borrowRequests.filter {
                it.state
            }
        } else {
            uiState.borrowRequests.filter {
                !it.state
            }
        }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Scaffold(
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp)) {
                    SearchTopBar(
                        search = { searchText ->
                            coroutineScope.launch {
                                viewModel.searchBorrowRequests(searchText)
                            }
                        },
                        placeholder = "Nhập mã đơn hoặc thông tin thành viên",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                    FilterByDateBar(
//                        day = uiState.day,
//                        month = uiState.month,
//                        year = uiState.year,
                        search = { day, month, year ->
                            coroutineScope.launch {
                                viewModel.searchByDate(day, month, year)
                            }
                        }
                    )
                    Divider(modifier = Modifier.shadow(4.dp))
            }
        },
        floatingActionButton = { AddButton(onClick = navigateToAddNewBorrowRequest) },
        bottomBar = {
            Column {
                BorrowStateBottomBar(
                    selectedTab = if(uiState.onReturned) 1 else 0,
                    onSwitch = viewModel::switchStateTab
                )
                HomeBottomAppBar(
                    currentTabIndex = 2,
                    navigateToBooksScreen = navigateToBooksScreen,
                    navigateToMembersScreen = navigateToMembersScreen,
                    navigateToSettingScreen = navigateToSettingScreen
                )
            }
        },
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ){ focusManager.clearFocus() }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            if(borrowRequests.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    items(borrowRequests) { borrowRequest ->
                        BorrowRequestInfo(
                           borrowRequest = borrowRequest,
                            onDelete = {
                                coroutineScope.launch {
                                    viewModel.deleteBorrowRequest(borrowRequest)
                                }
                            },
                            navigateToEditBorrowRequest = { navigateToEditBorrowRequest(borrowRequest.id) },
                            getMemberName = {
                                viewModel.getMemberName(it)
                            },
                            markReturned = { viewModel.markReturned(borrowRequest.id) },
                            modifier = Modifier.clickable { navigateToBorrowRequestDetail(borrowRequest.id) }
                        )
                    }
                }
            } else {
                Text(
                    text = "Chưa có đơn mượn nào!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun BorrowRequestInfo(
    borrowRequest: BorrowRequest,
    markReturned: () -> Unit,
    onDelete: () -> Unit,
    navigateToEditBorrowRequest: () -> Unit,
    getMemberName: suspend (Int) -> String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var memberName by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(borrowRequest.memberId) {
        memberName = getMemberName(borrowRequest.memberId)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(start = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.id, borrowRequest.id),
                    style = MaterialTheme.typography.titleMedium,
                    color = Title
                )
                Text(
                    text = memberName,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.ngay_muon, borrowRequest.borrowDate),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(
                onClick = { isExpanded = true },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Chỉnh sửa",
                    modifier = Modifier.rotate(90f)
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                ) {
//                    DropdownMenuItem(
//                        onClick = {
//                            isExpanded = false
//                            navigateToEditBorrowRequest()
//                        },
//                        text = {
//                            Text(
//                                text = "Sửa",
//                                style = MaterialTheme.typography.labelMedium,
//                                modifier = Modifier.padding(start = 20.dp)
//                            )
//                        },
//                        trailingIcon = {
//                            Icon(
//                                painter = painterResource(R.drawable.edit),
//                                contentDescription = "Sửa thông tin"
//                            )
//                        },
//                        modifier = Modifier.height(40.dp)
//                    )
//                    Divider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(1.dp)
//                    )
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            showDialog = true
                        },
                        text = {
                            Text(
                                text = "Xóa",
                                style = MaterialTheme.typography.labelMedium,
                                color = Delete,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "Xóa",
                                tint = Delete
                            )
                        },
                        modifier = Modifier.height(40.dp)
                    )

                    if(!borrowRequest.state) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                        DropdownMenuItem(
                            onClick = {
                                isExpanded = false
                                markReturned()
                            },
                            text = {
                                Text(
                                    text = "Đánh dấu đã trả",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MainColor,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            },
                            modifier = Modifier.height(40.dp)
                        )
                    }
                }
            }
        }
    }
    if(showDialog) {
        ConfirmDelete(
            title = "Xóa đơn mượn",
            content = stringResource(R.string.delete_borrow_warning, borrowRequest.id),
            onDelete = {
                onDelete()
                showDialog = false
            },
            onCancel = { showDialog = false }
        )
    }
}

//@Preview
//@Composable
//fun BorrowRequestsPreview() {
//    BorrowRequestsScreen(
//        navigateToAddNewBorrowRequest = {},
//        navigateToBooksScreen = {},
//        navigateToSettingScreen = {},
//        navigateToMembersScreen = {},
//        borrowRequests = listOf(
//            BorrowRequestDetail(
//                borrowId = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3,
//                exceptDate = " 25/06/2004",
//                returnDate = " 25/07/2004",
//                state = "Đã trả"
//            ),
//            BorrowRequestDetail(
//                borrowId = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3,
//                exceptDate = " 25/06/2004",
//                returnDate = " 25/07/2004",
//                state = "Đã trả"
//            )
//        )
//    )
//}