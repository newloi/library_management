package com.example.librarymanagement.ui.book

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.AddNumInfo
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmCancel
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.member.saveBitmapToInternalStorage
import com.example.librarymanagement.ui.member.saveImageToInternalStorage
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor
import kotlinx.coroutines.launch
import kotlin.text.ifEmpty

object AddNewBookDestination : NavigationDestination {
    override val route = "add_new_book"
}

@Composable
fun AddNewBookScreen(
    navigateDone: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddNewBookViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddAppBar(
                navigateBack = viewModel::showDialog,
                title = "Thêm sách"
            )
        }
    ) { innerPadding ->
        AddNewBook(
            onBookChange = viewModel::updateUiState,
            bookDetail = viewModel.bookUiState.bookDetail,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveBook()
                    navigateDone()
                }
            },
            enable = viewModel.bookUiState.isBookValid,
            modifier = Modifier.padding(innerPadding)
        )
        if(viewModel.bookUiState.isShowDialog) {
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

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun AddNewBook(
    onBookChange: (BookDetail) -> Unit,
    bookDetail: BookDetail,
    onSaveClick: () -> Unit,
    enable: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    // Trạng thái để lưu bitmap từ camera hoặc URI từ thư viện
    val imageUri = rememberSaveable { mutableStateOf(bookDetail.imageUri) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    // Bộ chọn ảnh từ thư viện
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri.value = it.toString()
            bitmap.value = null // Reset bitmap nếu chọn từ thư viện
        }
    }
    // Chụp ảnh từ camera
    val camLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if(it != null){
            bitmap.value = it
            imageUri.value = "" // Reset URI nếu chụp từ camera
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Column {
                    Text(
                        text = "Nhập ảnh",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (bitmap.value != null) {
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(117.dp, 140.dp)
                        )
                    } else if (imageUri.value.isNotEmpty()) {
                        AsyncImage(
                            model = imageUri.value,
                            contentDescription = null,
                            modifier = Modifier.size(117.dp, 140.dp)
                        )
                    } else {
                        Image(
                            rememberAsyncImagePainter(bookDetail.imageUri.ifEmpty { R.drawable.default_book }),
                            contentDescription = null,
                            modifier = Modifier.size(117.dp, 140.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Button(
                        onClick = { camLauncher.launch(null) },
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
                        onClick = { launcher.launch("image/*") },
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
        }
        item {
            Text(
                text = "Nhập thông tin sách",
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            AddInfo(
                onValueChange = { onBookChange(bookDetail.copy(name = it)) },
                value = bookDetail.name,
                label = "Tên sách",
                modifier = Modifier.fillMaxWidth()
            )
        }
//        OutlinedTextField(
//            label = {
//                Text(
//                    text = "Tên sách",
//                    style = MaterialTheme.typography.labelMedium
//                )
//            },
//            value = bookDetail.name,
//            textStyle = TextStyle(
//                fontFamily = Roboto,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.Black
//            ),
//            onValueChange = { onBookChange(bookDetail.copy(name = it)) },
//            singleLine = true,
//            shape = RoundedCornerShape(10.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//            keyboardActions = KeyboardActions(
//                onDone = {focusManager.clearFocus()}
//            ),
//            modifier = Modifier.height(60.dp).fillMaxWidth()
//        )
        item {
            AddInfo(
                onValueChange = { onBookChange(bookDetail.copy(author = it)) },
                value = bookDetail.author,
                label = "Tác giả",
                modifier = Modifier.fillMaxWidth()
            )
        }
//        OutlinedTextField(
//            label = {
//                Text(
//                    text = "Tác giả",
//                    style = MaterialTheme.typography.labelMedium
//                )
//            },
//            value = bookDetail.author,
//            textStyle = TextStyle(
//                fontFamily = Roboto,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.Black
//            ),
//            onValueChange = { onBookChange(bookDetail.copy(author = it)) },
//            singleLine = true,
//            shape = RoundedCornerShape(10.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//            keyboardActions = KeyboardActions(
//                onDone = {focusManager.clearFocus()}
//            ),
//            modifier = Modifier.height(60.dp).fillMaxWidth()
//        )
        item {
            AddInfo(
                onValueChange = { onBookChange(bookDetail.copy(publisher = it)) },
                value = bookDetail.publisher,
                label = "Nhà xuất bản",
                modifier = Modifier.fillMaxWidth()
            )
        }
//        OutlinedTextField(
//            label = {
//                Text(
//                    text = "Nhà xuất bản",
//                    style = MaterialTheme.typography.labelMedium
//                )
//            },
//            value = bookDetail.publisher,
//            textStyle = TextStyle(
//                fontFamily = Roboto,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.Black
//            ),
//            onValueChange = { onBookChange(bookDetail.copy(publisher = it)) },
//            singleLine = true,
//            shape = RoundedCornerShape(10.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//            keyboardActions = KeyboardActions(
//                onDone = {focusManager.clearFocus()}
//            ),
//            modifier = Modifier.height(60.dp).fillMaxWidth()
//        )
        item {
            Row {
                AddNumInfo(
                    onValueChange = { onBookChange(bookDetail.copy(year = it)) },
                    value = bookDetail.year,
                    label = "Năm xuất bản",
                    modifier = Modifier.width(120.dp)
                )
//            OutlinedTextField(
//                label = {
//                    Text(
//                        text = "Năm xuất bản",
//                        style = MaterialTheme.typography.labelMedium
//                    )
//                },
//                value = bookDetail.year,
//                textStyle = TextStyle(
//                    fontFamily = Roboto,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp,
//                    color = Color.Black
//                ),
//                onValueChange = { onBookChange(bookDetail.copy(year = it)) },
//                singleLine = true,
//                shape = RoundedCornerShape(10.dp),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//                keyboardActions = KeyboardActions(
//                    onDone = {focusManager.clearFocus()}
//                ),
//                modifier = Modifier.height(60.dp).width(120.dp)
//            )
                Spacer(modifier = Modifier.width(16.dp))
                AddInfo(
                    onValueChange = { onBookChange(bookDetail.copy(type = it)) },
                    value = bookDetail.type,
                    label = "Thể loại",
                    modifier = Modifier.width(200.dp)
                )
//            OutlinedTextField(
//                label = {
//                    Text(
//                        text = "Thể loại",
//                        style = MaterialTheme.typography.labelMedium
//                    )
//                },
//                value = bookDetail.type,
//                textStyle = TextStyle(
//                    fontFamily = Roboto,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp,
//                    color = Color.Black
//                ),
//                onValueChange = { onBookChange(bookDetail.copy(type = it)) },
//                singleLine = true,
//                shape = RoundedCornerShape(10.dp),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//                keyboardActions = KeyboardActions(
//                    onDone = {focusManager.clearFocus()}
//                ),
//                modifier = Modifier.height(60.dp).width(200.dp)
//            )
            }
        }
        item {
            AddNumInfo(
                onValueChange = { onBookChange(bookDetail.copy(quantities = it)) },
                value = bookDetail.quantities,
                label = "Số lượng",
                modifier = Modifier.width(120.dp)
            )
        }
//        OutlinedTextField(
//            label = {
//                Text(
//                    text = "Số lượng",
//                    style = MaterialTheme.typography.labelMedium
//                )
//            },
//            value = bookDetail.quantities,
//            textStyle = TextStyle(
//                fontFamily = Roboto,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.Black
//            ),
//            onValueChange = { onBookChange(bookDetail.copy(quantities = it)) },
//            singleLine = true,
//            shape = RoundedCornerShape(10.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//            keyboardActions = KeyboardActions(
//                onDone = {focusManager.clearFocus()}
//            ),
//            modifier = Modifier.height(60.dp)
//        )
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (bitmap.value != null) {
                            val savedUri = saveBitmapToInternalStorage(
                                context,
                                bitmap.value!!,
                                "book_${System.currentTimeMillis()}.jpg"
                            )
                            onBookChange(bookDetail.copy(imageUri = savedUri.toString()))
                        } else if (imageUri.value.isNotEmpty()) {
                            val savedUri = saveImageToInternalStorage(
                                context,
                                Uri.parse(imageUri.value),
                                "book_${System.currentTimeMillis()}.jpg"
                            )
                            onBookChange(bookDetail.copy(imageUri = savedUri.toString()))
                        }
                        onSaveClick()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    enabled = enable,
                    modifier = Modifier
//                        .align(Alignment.End)
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



@Preview(showBackground = true)
@Composable
fun AddNewBookScreenPreview() {
    LibraryManagementTheme { AddNewBookScreen(
        navigateDone = {},
        navigateBack = {}
    )
    }
}