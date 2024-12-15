package com.example.librarymanagement.ui.borrow
import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.DropList
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
object AddNewBorrowRequestDestination : NavigationDestination {
    override val route: String = "add_new_borrow_request"
}
@Composable
fun AddNewBorrowRequestScreen(
) {
    Scaffold(
        topBar = {
            AddAppBar(
                navigateBack = {},
                title = "Thêm đơn mượn"
            )
        }
    ) { innerPadding ->
        AddNewBorrowRequest(modifier = Modifier.padding(innerPadding))
    }
}
@Composable
fun AddNewBorrowRequest(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Thông tin đơn",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 20.dp)
        )
        AddInfo(
            label = "Họ và tên người mượn",
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
//                .padding(bottom = 10.dp)
        )
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            AddInfo(
                label = "Mã người mượn",
                value = "borrowRequest.borrowId.toString()",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            AddInfo(
                label = "Số sách mượn",
                value = "borrowRequest.bookCount.toString()",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            AddInfo(
                label = "Ngày mượn",
                value = "borrowRequest.borrowDate",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            AddInfo(
                label = "Ngày trả dự kiến",
                value = "borrowRequest.exceptDate",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )
        }
//        Row(modifier = Modifier.padding(bottom = 30.dp)) {
//            AddInfo(
//                label = "Ngày trả",
//                value = "borrowRequest.returnDate",
//                onValueChange = {},
//                modifier = Modifier.weight(1f)
//            )
//
//            Spacer(modifier = Modifier.width(40.dp))
//            DropList(
//                label = "Trạng thái",
//                items = listOf("Đã trả", "Chưa trả"),
//                onValueChange = {},
//                modifier = Modifier.weight(1f)
//            )
//        }
        Text(
            text = "Thêm sách mượn",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        ListBooks()
    }
}
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ListBooks(
    modifier: Modifier = Modifier
) {
    var bookIds by remember { mutableStateOf(listOf("")) }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Danh sách các TextField
        bookIds.forEachIndexed { index, code ->
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AddInfo(
                        value = code,
                        onValueChange = { newValue ->
                            bookIds = bookIds.toMutableList().apply {
                                this[index] = newValue
                            }
                        },
                        label = "Mã sách ${index + 1}",
                        modifier = Modifier.weight(1f)
                    )
                    if (code.isNotBlank()) {
                        AddInfo(
                            label = "Tên sách",
                            value = "Cho nay hien ten sach",
                            onValueChange = {},
                            modifier = Modifier.weight(1.5f)
                        )
                    }
                    IconButton(
                        onClick = {
                            bookIds = bookIds.toMutableList().apply {
                                removeAt(index)
                            }
                        },
                        enabled = bookIds.size > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Xóa mã sách",
                            tint = if (bookIds.size > 1) Delete else Cancel
                        )
                    }
                }
            }
        }
        // Nút thêm ô nhập mới
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { bookIds += "" },
                    modifier = Modifier
                        .border(
                            2.dp,
                            Color(0xFF4CAF50),
                            shape = RoundedCornerShape(16.dp)
                        ) // Thêm viền cho IconButton
                        .size(60.dp, 40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm mã sách",
                        tint = Color(0xFF4CAF50)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // Nút xác nhận
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    enabled = bookIds.all { it.isNotBlank() },
                    modifier = Modifier
//                    .align(Alignment.End)
                        .size(100.dp, 40.dp)
                ) {
                    Text(
                        text = "Xong",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun BRDPreview() {
    AddNewBorrowRequestScreen()
}