package com.example.librarymanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.BorrowRequest
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Title

@Composable
fun BorrowRequestsScreen(
    borrowRequests: List<BorrowRequest>
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().shadow(2.dp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SearchTopBar(
                        placeholder = "Nhập mã đơn hoặc tên thành viên",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                    FilterByDateBar()
                }
            }
        },
        floatingActionButton = { AddButton(onClick = {}) },
        bottomBar = {
            Column {
                BorrowStateBottomBar()
                HomeBottomAppBar()
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            items(borrowRequests) { borrowRequest ->
                BorrowRequest(borrowRequest)
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
                    HorizontalDivider(
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
                    HorizontalDivider(
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

@Preview
@Composable
fun BorrowRequestsPreview() {
    BorrowRequestsScreen(
        borrowRequests = listOf(
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
            BorrowRequest(
                id = 1,
                memberName = "Nguyen Van A",
                bookName = "Cau truc du lieu va giai thuat",
                borrowDate = "01/12/2024",
                bookCount = 3," 25/06/2004"," 25/07/2004","Đã trả"
            ),
        )
    )
}