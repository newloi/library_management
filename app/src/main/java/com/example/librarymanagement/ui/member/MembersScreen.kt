package com.example.librarymanagement.ui.member

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.librarymanagement.R
import com.example.librarymanagement.data.member.Member
import com.example.librarymanagement.ui.AddButton
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmDelete
import com.example.librarymanagement.ui.FilterBar
import com.example.librarymanagement.ui.HomeBottomAppBar
import com.example.librarymanagement.ui.SearchTopBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.BorderGray
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.Title
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.Locale

/** Thiết kế màn hình hiển thị list thanh vien */

object MembersDestination : NavigationDestination {
    override val route = "members"
}

/**
 * Man hinh home o tab Member, hien thi danh sach [Members] dang co
 */
@Composable
fun MembersScreen(
    navigateToAddNewMember: () -> Unit,
    navigateToBooksScreen: () -> Unit,
    navigateToBorrowRequestsScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToEditMember: (Int) -> Unit,
    navigateToMemberDetailScreen: (Int) -> Unit,
    membersScreenViewModel: MembersScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
//    members: List<Member> = listOf(
//        Member(
//            id = 0,
//            name = "Phan Minh Vuong",
//            gender = "Female",
//            dateOfBirth = "27/11/2024",
//            address = "DHBKHN",
//            registrationDate = "27/11/2024"
//        ),
//        Member(
//            id = 0,
//            name = "Phan Minh Vuong",
//            gender = "Female",
//            dateOfBirth = "27/11/2024",
//            address = "DHBKHN",
//            registrationDate = "27/11/2024"
//        )
//    ),
    modifier: Modifier = Modifier
) {
    val membersScreenUiState by membersScreenViewModel.membersScreenUiState.collectAsState()
    val vietnameseCollator = Collator.getInstance(Locale("vi", "VN"))
    val members =
        if(membersScreenUiState.isSortIncreasing) {
            when(membersScreenUiState.sortBy) {
                0 -> membersScreenUiState.members.sortedWith(compareBy(vietnameseCollator) { it.name })
                1 -> membersScreenUiState.members.sortedBy { it.id }
                2 -> membersScreenUiState.members.sortedBy { it.registrationDate }
                else -> membersScreenUiState.members.sortedWith(compareBy(vietnameseCollator) { it.name })
            }
        } else {
            when(membersScreenUiState.sortBy) {
                0 -> membersScreenUiState.members.sortedWith(compareByDescending(vietnameseCollator) { it.name })
                1 -> membersScreenUiState.members.sortedByDescending { it.id }
                2 -> membersScreenUiState.members.sortedByDescending { it.registrationDate }
                else -> membersScreenUiState.members.sortedWith(compareByDescending(vietnameseCollator) { it.name })
            }
        }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 56.dp)) {
                SearchTopBar(
                    search = { searchText ->
                        coroutineScope.launch {
                            membersScreenViewModel.searchMembers(searchText)
                        }
                    },
                    placeholder = "Nhập tên hoặc mã thành viên",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
                FilterBar(
                    isIncreasing = membersScreenUiState.isSortIncreasing,
                    onSortByOpt0 = { membersScreenViewModel.setSortBy(0) },
                    onSortByOpt1 = { membersScreenViewModel.setSortBy(1) },
                    onSortByOpt2 = { membersScreenViewModel.setSortBy(2) },
                    sortOptions = listOf("Xếp theo tên", "Xếp theo mã số", "Xếp theo ngày đăng kí"),
                    onToggleSortOrder = membersScreenViewModel::toggleSortOrder
                )
                Divider(modifier = Modifier.shadow(4.dp))
            }
        },
        floatingActionButton = { AddButton(onClick = navigateToAddNewMember) },
        bottomBar = {
            HomeBottomAppBar(
                currentTabIndex = 1,
                navigateToBooksScreen = navigateToBooksScreen,
                navigateToBorrowRequestsScreen = navigateToBorrowRequestsScreen,
                navigateToSettingScreen = navigateToSettingScreen,
                modifier = Modifier.shadow(1.dp)
            )
        },
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ){ focusManager.clearFocus() }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding).fillMaxSize()) {
            if(members.isNotEmpty()){
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(members) { member ->
                        MemberInfo(
                            navigateToEditMember = { navigateToEditMember(member.id) },
                            onDelete = {
                                coroutineScope.launch {
                                    membersScreenViewModel.deleteMember(member)
                                }
                            },
                            member = member,
                            modifier = Modifier.clickable { navigateToMemberDetailScreen(member.id) }

                        )
                    }
                }
            } else {
                Text(
                    text = "Chưa có thành viên nào!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                )
            }
        }
    }
}

/**
 * The bieu dien cho thanh vien [Member] o tab Member
 */
@Composable
private fun MemberInfo(
    member: Member,
    onDelete: () -> Unit,
    navigateToEditMember: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Row(modifier = Modifier
                .padding(12.dp)
                .weight(1f)) {
                Image(
                    painter  = rememberAsyncImagePainter(member.imageUri.ifEmpty { R.drawable.defaulr_avatar }),
                    contentDescription = member.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(65.dp)
                        .border(
                            width = 1.5.dp,
                            color = BorderGray,
                            shape = CircleShape
                        )
                        .clip(CircleShape),

                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = member.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Title
                    )
                    Text(
                        text = stringResource(R.string.ma_so, member.id),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(R.string.ngay_dang_ki, member.registrationDate),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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
                            navigateToEditMember()
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
                }
            }
        }
    }
    if(showDialog) {
        ConfirmDelete(
            title = "Xóa thành viên",
            content = stringResource(R.string.delete_member_warning, member.name),
            onDelete = {
                onDelete()
                showDialog = false
            },
            onCancel = { showDialog = false }
        )
    }
}

/**
 * Hop thoai hien ra khi xoa
 */
//@Composable
//private fun DialogConfirmDeleteMember(
//    nameOfMember: String,
//    modifier: Modifier = Modifier
//) {
//    ConfirmDialog(
//        title = "Xóa thành viên",
//        content = stringResource(R.string.delete_member_warning, nameOfMember),
//        onConfirm = {},
//        onCancel = {},
//        cancelLabel = "Không",
//        confirmLabel = "Xóa",
//        cancelColor = Cancel,
//        confirmColor = Delete,
//        alpha = 0.66f,
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun DialogMemberPreview() {
//    LibraryManagementTheme {
//        DialogConfirmDeleteMember(nameOfMember = "Cau truc du lieu va giai thuat")
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMember() {
//    LibraryManagementTheme {
//        MembersScreen(
//            navigateToBorrowRequestsScreen = {},
//            navigateToAddNewMember = {},
//            navigateToBooksScreen = {},
//            members = listOf(
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                ),
//                Member(
//                    id = 0,
//                    name = "Phan Minh Vuong",
//                    gender = "Female",
//                    dateOfBirth = "27/11/2024",
//                    address = "DHBKHN",
//                    registrationDate = "27/11/2024"
//                )
//            )
//        )
//    }
//}
