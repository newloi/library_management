package com.example.librarymanagement.ui.member

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.librarymanagement.R
import com.example.librarymanagement.data.member.Member
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmDelete
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object MemberDetailDestination : NavigationDestination {
    override val route = "member_detail"
    const val memberIdArg = "memberId"
    val routeWithArgs = "$route/{$memberIdArg}"
}

@Composable
fun MemberDetailScreen(
//    @DrawableRes memberImage: Int =  R.drawable.book,
//    member: Member =  Member(
//                name = "Lưu Ngọc Lợi",
//                gender = "Nam",
//                dateOfBirth = "24/05/2004",
//                address = "Hưng Yên",
//                registrationDate = "22/11/2024"
//            ),
    navigateToEditMember: (Int) -> Unit,
    navigateBack: () -> Unit,
    navigateDone: () -> Unit,
    memberDetailViewModel: MemberDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val memberDetailUiState by memberDetailViewModel.uiState.collectAsState()
    var isShowDialog by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            InfoAppBar(
                navigateToEdit = { navigateToEditMember(memberDetailUiState.currentMember.id) },
                navigateBack = navigateBack,
                onDelete = { isShowDialog = true },
                title = stringResource(R.string.thanh_vien, memberDetailUiState.currentMember.id)
            )
        }
    ) { innerPadding ->
        MemberDetail(
//            bookImage = bookImage,
            member = memberDetailUiState.currentMember,
            modifier = modifier.padding(innerPadding)
        )
        if(isShowDialog) {
            ConfirmDelete(
                title = "Xóa thành viên",
                content = stringResource(R.string.delete_member_warning, memberDetailUiState.currentMember.name),
                onDelete = {
                    coroutineScope.launch {
                        memberDetailViewModel.deleteMember()
                        isShowDialog = false
                        navigateDone()
                    }
                },
                onCancel = { isShowDialog = false }
            )
        }
    }
}

@Composable
private fun MemberDetail(
//    @DrawableRes memberImage: Int,
    member: Member,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Ảnh",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        item {
            Image(
                painter = rememberAsyncImagePainter(member.imageUri.ifEmpty { R.drawable.default_avatar }),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(117.dp, 140.dp)
            )
        }
        item {
            Text(
                text = "Thông tin thành viên",
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            InfoAbout(
                label = "Họ và tên",
                value = member.name,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                InfoAbout(
                    label = "Giới tính",
                    value = member.gender,
                    modifier = Modifier.width(100.dp)
                )
//                Spacer(modifier = modifier.width(32.dp))
                InfoAbout(
                    label = "Ngày sinh",
                    value = member.dateOfBirth,
//                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            InfoAbout(
                label = "Địa chỉ",
                value = member.address,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            InfoAbout(
                label = "Ngày đăng kí",
                value = member.registrationDate,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MemberDetailPreview() {
//    LibraryManagementTheme {
//        MemberDetailScreen(
//            memberImage = R.drawable.book,
//            member = Member(
//                name = "Lưu Ngọc Lợi",
//                gender = "Nam",
//                dateOfBirth = "24/05/2004",
//                address = "Hưng Yên",
//                registrationDate = "22/11/2024"
//            ),
//            navigateBack = {},
//            navigateToEditMember = {}
//        )
//    }
//}