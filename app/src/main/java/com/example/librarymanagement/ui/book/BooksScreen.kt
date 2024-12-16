package com.example.librarymanagement.ui.book

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.librarymanagement.R
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.ui.AddButton
import com.example.librarymanagement.ui.FilterBar
import com.example.librarymanagement.ui.HomeBottomAppBar
import com.example.librarymanagement.ui.SearchTopBar
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmDelete
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.Title
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.Locale

/** Thiết kế màn hình hiển thị list sách */

object BooksDestination : NavigationDestination {
    override val route = "books"
}

/**
 * Man hinh home o tab Sach, hien thi danh sach [books] dang co
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BooksScreen(
    navigateToAddNewBook: () -> Unit,
    navigateToMembersScreen: () -> Unit,
    navigateToBorrowRequestsScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToEditBook: (Int) -> Unit,
    navigateToBookDetailScreen: (Int) -> Unit,
    viewModel: BooksScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
//    books: List<Book> = listOf(
//        Book(
//            id = 0,
//            name = "Cau truc du lieu va giai thuat",
//            author = "Nguyen Tuan Dung",
//            publisher = "BKHN",
//            year = 2024,
//            type = "IT",
//            quantities = 2
//        ),
//        Book(
//            id = 0,
//            name = "Cau truc du lieu va giai thuat",
//            author = "Nguyen Tuan Dung",
//            publisher = "BKHN",
//            year = 2024,
//            type = "IT",
//            quantities = 2
//        )
//    ),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.booksScreenUiState.collectAsState()
    val vietnameseCollator = Collator.getInstance(Locale("vi", "VN"))
    val books =
        if(uiState.isSortIncreasing) {
            when(uiState.sortBy) {
                0 -> uiState.books.sortedWith(compareBy(vietnameseCollator) { it.name })
                1 -> uiState.books.sortedBy { it.type }
                2 -> uiState.books.sortedBy { it.quantities }
                else -> uiState.books.sortedWith(compareBy(vietnameseCollator) { it.name })
            }
        } else {
            when(uiState.sortBy) {
                0 -> uiState.books.sortedWith(compareByDescending(vietnameseCollator) { it.name })
                1 -> uiState.books.sortedByDescending { it.type }
                2 -> uiState.books.sortedByDescending { it.quantities }
                else -> uiState.books.sortedWith(compareByDescending(vietnameseCollator) { it.name })
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
                            viewModel.searchBooks(searchText)
                        }
                    },
                    placeholder = "Nhập tên hoặc mã sách",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
                FilterBar(
                    isIncreasing = uiState.isSortIncreasing,
                    onSortByOpt0 = { viewModel.setSortBy(0) },
                    onSortByOpt1 = { viewModel.setSortBy(1) },
                    onSortByOpt2 = { viewModel.setSortBy(2) },
                    sortOptions = listOf("Xếp theo tên", "Xếp theo thể loại", "Xếp theo số lượng"),
                    onToggleSortOrder = viewModel::toggleSortOrder
                )
                Divider(modifier = Modifier.shadow(4.dp))
            }
        },
        floatingActionButton = { AddButton(onClick = navigateToAddNewBook) },
        bottomBar = {
            HomeBottomAppBar(
                currentTabIndex = 0,
                navigateToMembersScreen = navigateToMembersScreen,
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
        Box(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            if(books.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    items(items = books, key = {it.id}) { book ->
                        BookInfo(
                            navigateToEditBook = { navigateToEditBook(book.id) },
                            onDelete = {
                                coroutineScope.launch {
                                    viewModel.deleteBook(book)
                                }
                            },
                            book = book,
                            modifier = Modifier.clickable { navigateToBookDetailScreen(book.id) }
                        )
                    }
                }
            } else {
                Text(
                    text = "Không có sách nào!",
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

/**
 * The bieu dien cho cuon sach [book] o tab Sach
 */
@Composable
private fun BookInfo(
    book: Book,
    onDelete: () -> Unit,
    navigateToEditBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(124.dp)
        ,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Cancel)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Row(modifier = Modifier
                .padding(12.dp)
                .weight(1f)) {
                Image(
                    painter = rememberAsyncImagePainter(book.imageUri.ifEmpty { R.drawable.default_book }),
                    contentDescription = book.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(81.dp, 97.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = book.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Title
                    )
                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Thể loại: " + book.type,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle()) {
                                append("Số lượng: ")
                            }
                            withStyle(style = MaterialTheme.typography.titleSmall.toSpanStyle()) {
                                append(book.quantities.toString())
                            }
                        }
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
                    contentDescription = "Chỉnh sửa"
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                ) {
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            navigateToEditBook()
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
            title = "Xóa sách",
            content = stringResource(R.string.delete_book_warning, book.name),
            onDelete = {
                onDelete()
                showDialog = false
            },
            onCancel = { showDialog = false }
        )
    }
}

/**
 * Hop thoai hien ra khi nhan vao dau ba cham cua the sach [nameOfBook]
 */


//@Preview(showBackground = true)
//@Composable
//fun DialogPreview() {
//    LibraryManagementTheme {
//        DialogConfirmDeleteBook(nameOfBook = "Cau truc du lieu va giai thuat")
//    }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    LibraryManagementTheme {
        BooksScreen(
            navigateToAddNewBook = {},
            navigateToBookDetailScreen = {},
            navigateToEditBook = {},
            navigateToBorrowRequestsScreen = {},
            navigateToMembersScreen = {},
            navigateToSettingScreen = {},
        )
    }
}
