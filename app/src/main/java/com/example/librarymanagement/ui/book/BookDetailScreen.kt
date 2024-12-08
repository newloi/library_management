package com.example.librarymanagement.ui.book

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme

object BookDetailDestination : NavigationDestination {
    override val route = "book_detail"
    const val bookIdArg = "bookId"
    val routeWithArgs = "$route/{$bookIdArg}"
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
//    @DrawableRes bookImage: Int = 0,
    book: Book = Book(name = "Cau truc du lieu va giai thuat",
        author = "Nguyen Tuan Dung",
        publisher = "NXB Back Khoa",
        year = 2024,
        type = "IT",
        quantities = 3),
    navigateToEditBook: () -> Unit,
    navigateBack: () -> Unit,
    bookDetailViewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val bookDetailUiState by bookDetailViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            InfoAppBar(
                navigateToEdit = navigateToEditBook,
                navigateBack = navigateBack,
                title = stringResource(R.string.sach, book.id))
        }
    ) { innerPadding ->
        BookDetail(
//            bookImage = bookImage,
            book = bookDetailUiState.currentBook,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun BookDetail(
//    @DrawableRes bookImage: Int,
    book: Book,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
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
                painter = painterResource(R.drawable.lamda_image),
                contentDescription = "Hình ảnh minh họa của sách",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(117.dp, 140.dp)
            )
        }
        item {
            Text(
                text = "Thông tin sách",
                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        item {
            InfoAbout(
                label = "Tên sách",
                value = book.name,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            InfoAbout(
                label = "Tác giả",
                value = book.author,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            InfoAbout(
                label = "Nhà xuất bản",
                value = book.publisher,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoAbout(
                    label = "Năm xuất bản",
                    value = book.year.toString(),
                    modifier = Modifier.width(120.dp)
                )
                InfoAbout(
                    label = "Thể loại",
                    value = book.type,
                    modifier = Modifier.width(200.dp)
                )
            }
        }
        item {
            InfoAbout(
                label = "Số lượng",
                value = book.quantities.toString(),
                modifier = Modifier.width(120.dp)
            )
        }
    }

}



@Preview(showBackground = true)
@Composable
fun BookDetailBodyPreview() {
    LibraryManagementTheme {
        BookDetailScreen(
//            bookImage = R.drawable.lamda_image,
            navigateToEditBook = {},
            navigateBack = {}
        )
    }
}