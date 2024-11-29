package com.example.librarymanagement.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.LoginBackground
import com.example.librarymanagement.ui.theme.Title

/** Thiết kế màn hình hiển thị list sách */

/**
 * Man hinh home o tab Sach, hien thi danh sach [books] dang co
 */
@Composable
fun BooksScreen(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { SearchAndFilterTopAppBar(modifier = Modifier.shadow(2.dp).fillMaxWidth()) },
        floatingActionButton = { AddButton(onClick = {}) },
        bottomBar = { HomeBottomAppBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            items(books) { book ->
                BookInfo(book = book)
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
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(124.dp),
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
                    painter = painterResource(R.drawable.rectangle_4165),
                    contentDescription = book.name,
                    modifier = Modifier.size(81.dp, 97.dp)
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
                }
            }
        }
    }
}

/**
 * Hop thoai hien ra khi nhan vao dau ba cham cua the sach [nameOfBook]
 */
@Composable
fun DialogConfirmDeleteBook(
    nameOfBook: String,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(
                text = "Xóa sách",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = stringResource(R.string.delete_book_warning, nameOfBook),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Delete),
                modifier = Modifier.size(100.dp, 44.dp).alpha(0.66f)
            ) {
                Text(
                    text = "Xóa",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Cancel),
                modifier = Modifier.size(100.dp, 44.dp)
            ) {
                Text(
                    text = "Không",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    LibraryManagementTheme {
        DialogConfirmDeleteBook(nameOfBook = "Cau truc du lieu va giai thuat")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LibraryManagementTheme {
        BooksScreen(
            books = listOf(
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
                Book(
                    name = "Cau truc du lieu va giai thuat hahahahah",
                    author = "Nguyen Tuan Dung",
                    publisher = "NXB Back Khoa",
                    year = 2024,
                    type = "IT",
                    quantities = 3
                ),
            )
        )
    }
}