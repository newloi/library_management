package com.example.librarymanagement.ui.member

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.ActivityNavigatorExtras
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmCancel
import com.example.librarymanagement.ui.DatePickerWithLabel
import com.example.librarymanagement.ui.DropList
import com.example.librarymanagement.ui.book.AddNewBook
import com.example.librarymanagement.ui.book.BookDetail
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor
import kotlinx.coroutines.launch

object AddNewMemberDestination : NavigationDestination {
    override val route = "add_new_member"
}

@Composable
fun AddNewMemberScreen(
    navigateDone: () -> Unit,
    navigateBack: () -> Unit,
    addNewMemberViewModel: AddNewMemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddAppBar(
                navigateBack = addNewMemberViewModel::showDialog,
                title = "Thêm thành viên"
            )
        }
    ) { innerPadding ->
        AddNewMember(
            onMemberChange = addNewMemberViewModel::updateUiState,
            memberDetail = addNewMemberViewModel.memberUiState.memberDetail,
            onSaveClick = {
                coroutineScope.launch {
                    addNewMemberViewModel.saveMember()
                    navigateDone()
                }
            },
            enable = addNewMemberViewModel.memberUiState.isMemberValid,
            modifier = Modifier.padding(innerPadding)
        )
        if(addNewMemberViewModel.memberUiState.isShowDialog) {
            ConfirmCancel(
                onDelete = {
                    addNewMemberViewModel.showDialog()
                    navigateBack()
                },
                onCancel = addNewMemberViewModel::showDialog
            )
        }
    }
}

@Composable
fun AddNewMember(
    onMemberChange: (MemberDetail) -> Unit,
    memberDetail: MemberDetail,
    onSaveClick: () -> Unit,
    enable: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    // Trạng thái để lưu bitmap từ camera hoặc URI từ thư viện
    val imageUri = rememberSaveable { mutableStateOf(memberDetail.imageUri) }
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
            Column() {
                Text(
                    text = "Nhập ảnh",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if(bitmap.value != null){
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(117.dp, 140.dp)
                    )
                }
                else if(imageUri.value.isNotEmpty()){
                    AsyncImage(
                        model = imageUri.value,
                        contentDescription = null,
                        modifier = Modifier.size(117.dp, 140.dp)
                    )
                }
                else{
                    Image(
                        rememberAsyncImagePainter(memberDetail.imageUri.ifEmpty { R.drawable.default_avatar }),
                        contentDescription = null,
                        modifier = Modifier.size(117.dp, 140.dp)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f).fillMaxHeight()
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
        Text(
            text = "Nhập thông tin thành viên",
            style = MaterialTheme.typography.titleLarge
        )
        AddInfo(
            onValueChange = { onMemberChange(memberDetail.copy(name = it)) },
            value = memberDetail.name,
            label = "Họ và tên",
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            DropList(
                value = memberDetail.gender,
                onValueChange = { onMemberChange(memberDetail.copy(gender = it)) },
                label = "Giới tính",
                items = listOf("Nam", "Nữ", "Khác"),
                modifier = Modifier.width(108.dp))
            Spacer(modifier = Modifier.width(16.dp))
            DatePickerWithLabel(
                label = "Ngày sinh",
                value = memberDetail.dateOfBirth,
                onValueChange = { onMemberChange(memberDetail.copy(dateOfBirth = it)) }
            )
        }
        AddInfo(
            onValueChange = { onMemberChange(memberDetail.copy(address = it)) },
            value = memberDetail.address,
            label = "Địa chỉ",
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerWithLabel(
            label = "Ngày đăng kí",
            value = memberDetail.registrationDate,
            onValueChange = { onMemberChange(memberDetail.copy(registrationDate = it)) }
        )
        Button(
            onClick = {
                if (bitmap.value != null) {
                    val savedUri = saveBitmapToInternalStorage(
                        context,
                        bitmap.value!!,
                        "member_${System.currentTimeMillis()}.jpg"
                    )
                    onMemberChange(memberDetail.copy(imageUri = savedUri.toString()))
                } else if (imageUri.value.isNotEmpty()) {
                    val savedUri = saveImageToInternalStorage(
                        context,
                        Uri.parse(imageUri.value),
                        "member_${System.currentTimeMillis()}.jpg"
                    )
                    onMemberChange(memberDetail.copy(imageUri = savedUri.toString()))
                }
                onSaveClick()
            },
            enabled = enable,
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            modifier = Modifier.align(Alignment.End).size(100.dp, 40.dp)
        ) {
            Text(
                text = "Xong",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddNewMemberScreenPreview() {
//    LibraryManagementTheme { AddNewMemberScreen() }
//}