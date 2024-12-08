package com.example.librarymanagement.ui.book

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmDialog
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Roboto
import kotlinx.coroutines.launch

object AddNewBookDestination : NavigationDestination {
    override val route = "add_new_book"
}

@Composable
fun AddNewBookScreen(
//    navigateDone: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddNewBookViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddAppBar(
                navigateBack = navigateBack,
                title = "Thêm sách"
            )
        }
    ) { innerPadding ->
        AddNewBook(
            onBookChange = viewModel::updateUiState,
            bookDetail = viewModel.bookUiState.bookDetail,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            enable = viewModel.bookUiState.isBookValid,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun AddNewBook(
    onBookChange: (BookDetail) -> Unit,
    bookDetail: BookDetail,
    onSaveClick: () -> Unit,
    enable: Boolean = true,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ){ focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            Column {
                Text(
                    text = "Nhập ảnh",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Image(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = null,
                    modifier = Modifier.size(117.dp, 140.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    modifier = Modifier.size(150.dp, 34.dp)
                ) {
                    Text(
                        text = "Chụp ảnh",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    modifier = Modifier.size(150.dp, 34.dp)
                ) {
                    Text(
                        text = "Chọn từ thư viện",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
        Text(
            text = "Nhập thông tin sách",
            style = MaterialTheme.typography.titleLarge
        )
//        AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Tên sách", modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            label = {
                Text(
                    text = "Tên sách",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            value = bookDetail.name,
            textStyle = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            ),
            onValueChange = { onBookChange(bookDetail.copy(name = it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            modifier = Modifier.height(60.dp).fillMaxWidth()
        )
//        AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Tác giả", modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            label = {
                Text(
                    text = "Tác giả",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            value = bookDetail.author,
            textStyle = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            ),
            onValueChange = { onBookChange(bookDetail.copy(author = it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            modifier = Modifier.height(60.dp).fillMaxWidth()
        )
//        AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Nhà xuất bản", modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            label = {
                Text(
                    text = "Nhà xuất bản",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            value = bookDetail.publisher,
            textStyle = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            ),
            onValueChange = { onBookChange(bookDetail.copy(publisher = it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            modifier = Modifier.height(60.dp).fillMaxWidth()
        )
        Row {
//            AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Năm xuất bản", modifier = Modifier.width(120.dp))
            OutlinedTextField(
                label = {
                    Text(
                        text = "Năm xuất bản",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                value = bookDetail.year,
                textStyle = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                onValueChange = { onBookChange(bookDetail.copy(year = it)) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                ),
                modifier = Modifier.height(60.dp).width(120.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
//            AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Thể loại", modifier = Modifier.width(200.dp))
            OutlinedTextField(
                label = {
                    Text(
                        text = "Thể loại",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                value = bookDetail.type,
                textStyle = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                onValueChange = { onBookChange(bookDetail.copy(type = it)) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                ),
                modifier = Modifier.height(60.dp).width(200.dp)
            )
        }
//        AddInfo(onValueChange = onBookChange, bookDetail = bookUiState.bookDetail, label = "Số lượng", modifier = Modifier.width(120.dp))
        OutlinedTextField(
            label = {
                Text(
                    text = "Số lượng",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            value = bookDetail.quantities,
            textStyle = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            ),
            onValueChange = { onBookChange(bookDetail.copy(quantities = it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            modifier = Modifier.height(60.dp)
        )
        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            enabled = enable,
            modifier = Modifier.align(Alignment.End).size(100.dp, 40.dp)
        ) {
            Text(
                text = "Xong",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun ConfirmCancel(modifier: Modifier = Modifier) {
    ConfirmDialog(
        title = "Hủy thay đổi",
        content = "Các dữ liệu đã hủy sẽ không được lưu. Xác nhận hủy?",
        cancelLabel = "Giữ lại",
        confirmLabel = "Hủy",
        cancelColor = Cancel,
        confirmColor = MainColor,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun AddNewBookScreenPreview() {
    LibraryManagementTheme { AddNewBookScreen(
//        navigateDone = {},
        navigateBack = {})
    }
}