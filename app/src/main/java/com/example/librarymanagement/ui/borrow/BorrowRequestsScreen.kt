package com.example.librarymanagement.ui.borrow

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.BorrowRequest
import com.example.librarymanagement.ui.AddButton
import com.example.librarymanagement.ui.BorrowStateBottomBar
import com.example.librarymanagement.ui.FilterByDateBar
import com.example.librarymanagement.ui.HomeBottomAppBar
import com.example.librarymanagement.ui.SearchTopBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Title

object BorrowRequestsDestination : NavigationDestination {
    override val route = "borrow_requests"
//    override val title = ""
}

@Composable
fun BorrowRequestsScreen(
    navigateToAddNewBorrowRequest: () -> Unit,
    navigateToBooksScreen: () -> Unit,
    navigateToMembersScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    borrowRequests: List<BorrowRequest> = listOf(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SearchTopBar(
                        search = {},
                        placeholder = "Nhập mã đơn hoặc tên thành viên",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                    FilterByDateBar()
                }
            }
        },
        floatingActionButton = { AddButton(onClick = navigateToAddNewBorrowRequest) },
        bottomBar = {
            Column {
                BorrowStateBottomBar()
                HomeBottomAppBar(
                    currentTabIndex = 2,
                    navigateToBooksScreen = navigateToBooksScreen,
                    navigateToMembersScreen = navigateToMembersScreen,
                    navigateToSettingScreen = navigateToSettingScreen
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding).fillMaxSize()) {
            if(borrowRequests.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    items(borrowRequests) { borrowRequest ->
                        BorrowRequest(borrowRequest)
                    }
                }
            } else {
                Text(
                    text = "Chưa có đơn mượn nào!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun BorrowRequest(
    borrowRequest: BorrowRequest,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(start = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        var isExpanded by remember { mutableStateOf(false) }

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
                    text = borrowRequest.memberName,
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
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            /* Sua thong tin */
                        },
                        text = {
                            Text(
                                text = "Sửa",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = "Sửa thông tin"
                            )
                        },
                        modifier = Modifier.height(40.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            /* Xoa */
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
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            /* Danh dau da tra */
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

//@Preview
//@Composable
//fun BorrowRequestsPreview() {
//    BorrowRequestsScreen(
//        borrowRequests = listOf(
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//            BorrowRequest(
//                id = 1,
//                memberName = "Nguyen Van A",
//                bookName = "Cau truc du lieu va giai thuat",
//                borrowDate = "01/12/2024",
//                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
//            ),
//        )
//    )
//}