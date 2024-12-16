package com.example.librarymanagement.ui.borrow

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmCancel
import com.example.librarymanagement.ui.DatePickerWithLabel
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Warning
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object AddNewBorrowRequestDestination : NavigationDestination {
    override val route: String = "add_new_borrow_request"
}

@Composable
fun AddNewBorrowRequestScreen(
    navigateDone: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddNewBorrowRequestViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddAppBar(
                navigateBack = viewModel::showDialog,
                title = "Thêm đơn mượn"
            )
        }
    ) { innerPadding ->
        AddNewBorrowRequest(
            onBorrowChange = viewModel::updateUiState,
            borrowDetail = viewModel.borrowUiState.borrowDetail,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.reduceQuantityByOne()
                    viewModel.saveBorrow()
                    navigateDone()
                }
            },
            getBookName = { bookId ->
                viewModel.getBookName(bookId)
            },
            getMemberName = { memberId ->
                viewModel.getMemberName(memberId)
            },
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

@SuppressLint("DefaultLocale")
@Composable
fun AddNewBorrowRequest(
    onBorrowChange: (BorrowDetail) -> Unit,
    borrowDetail: BorrowDetail,
    onSaveClick: () -> Unit,
    getBookName: suspend (Int) -> String,
    getMemberName: suspend (Int) -> String,
    isOutOfStock: suspend (Int) -> Boolean,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
//    var countBook by rememberSaveable { mutableIntStateOf(0) }
    var bookIds by rememberSaveable { mutableStateOf(listOf("")) }
//    LaunchedEffect(bookIds) {
//        countBook =
//            bookIds.count { it.isNotBlank() && (getBookName(it.toInt()) != "Mã sách không đúng!") }
//    }
    LaunchedEffect(bookIds) {
        onBorrowChange(
            borrowDetail.copy(countBook = bookIds.count {
                it.isNotBlank()
                        && (getBookName(it.toInt()) != "Mã sách không đúng!")
                        && !isOutOfStock(it.toInt())
            })
        )
//        onBorrowChange(
//            borrowDetail.copy(countTotal = bookIds.size)
//        )
    }
    LaunchedEffect(borrowDetail.memberId) {
        onBorrowChange(
            borrowDetail.copy(memberName = getMemberName(borrowDetail.memberId.toIntOrNull() ?: 0))
        )
    }

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
//        var memberName by rememberSaveable { mutableStateOf("") }
//        LaunchedEffect(borrowDetail.memberId) {
//            if (borrowDetail.memberId.isNotBlank()) {
//                memberName = getMemberName(borrowDetail.memberId.toInt())
//            } else {
//                memberName = ""
//            }
//        }

        AddInfo(
            label = "Họ và tên người mượn",
            value = borrowDetail.memberName,
            onValueChange = {},
            canEdit = false,
            modifier = Modifier
                .fillMaxWidth()
//                .padding(bottom = 10.dp)
        )
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            AddInfo(
                label = "Mã người mượn",
                value = borrowDetail.memberId,
                onValueChange = { onBorrowChange(borrowDetail.copy(memberId = it)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            AddInfo(
                label = "Số sách mượn",
                value = borrowDetail.countBook.toString(),
                onValueChange = {},
                canEdit = false,
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            DatePickerWithLabel(
                label = "Ngày mượn",
                value = borrowDetail.borrowDate,
                onValueChange = { onBorrowChange(borrowDetail.copy(borrowDate = it)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            DatePickerWithLabel(
                label = "Ngày trả dự kiến",
                value = borrowDetail.exceptDate,
                onValueChange = { onBorrowChange(borrowDetail.copy(exceptDate = it)) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            bookIds.forEachIndexed { index, code ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        var showWarning by rememberSaveable { mutableStateOf(false) }
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
                            LaunchedEffect(code) {
//                                if(code.isNotBlank()) {
                                    showWarning = isOutOfStock(code.toIntOrNull() ?: 0)
//                                }
                            }

                            // Tạo biến lưu tên sách
                            val bookName = rememberSaveable { mutableStateOf("") }

                            // Khi mã sách thay đổi, gọi getBookName để lấy tên sách
                            LaunchedEffect(code) {
                                if (code.isNotBlank()) {
                                    bookName.value = getBookName(code.toInt())
                                }
                            }

                            if (code.isNotBlank()) {
                                AddInfo(
                                    label = "Tên sách",
                                    value = bookName.value,
                                    onValueChange = {},
                                    canEdit = false,
                                    modifier = Modifier.weight(1.5f)
                                )
                            }

                            // Xử lý xóa mã sách
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
                        if(showWarning) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.error),
                                    contentDescription = null,
                                    tint = Warning
                                )
                                Text(
                                    text = "Hết sách!",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Warning
                                )
                            }
                        }
                    }
                }
            }

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
                            )
                            .size(60.dp, 40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Thêm mã sách",
                            tint = Color(0xFF4CAF50)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            onBorrowChange(borrowDetail.copy(listBookIds = bookIds))
                            onSaveClick()
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        enabled = (bookIds.size == borrowDetail.countBook) && (bookIds.size == bookIds.distinct().size) && enable ,
                        modifier = Modifier.size(100.dp, 40.dp)
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
}


@Preview
@Composable
fun BRDPreview() {
    AddNewBorrowRequestScreen(
        navigateDone = {},
        navigateBack = {}
    )
}