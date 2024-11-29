package com.example.librarymanagement.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Book
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.Roboto

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    @DrawableRes bookImage: Int,
    book: Book,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { InfoAppBar() }
    ) { innerPadding ->
        BookDetail(
            bookImage = bookImage,
            book = book,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun BookDetail(
    @DrawableRes bookImage: Int,
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Ảnh",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )
        Image(
            painter = painterResource(bookImage),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally).size(117.dp, 140.dp)
        )
        Text(
            text = "Thông tin sách",
            style = MaterialTheme.typography.titleLarge
        )
        InfoAbout(
            label = "Tên",
            value = book.name,
            modifier = Modifier.fillMaxWidth()
        )
        InfoAbout(
            label = "Tác giả",
            value = book.author,
            modifier = Modifier.fillMaxWidth()
        )
        InfoAbout(
            label = "Nhà xuất bản",
            value = book.publisher,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier) {
            InfoAbout(
                label = "Năm",
                value = book.year.toString(),
                modifier = Modifier.width(120.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            InfoAbout(
                label = "Thể loại",
                value = book.type,
                modifier = Modifier.width(200.dp)
            )
        }
        InfoAbout(
            label = "Số lượng",
            value = book.quantities.toString(),
            modifier = Modifier.width(120.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun BookDetailBodyPreview() {
    LibraryManagementTheme {
        BookDetailScreen(
            bookImage = R.drawable.lamda_image,
            book = Book(
                name = "Cau truc du lieu va giai thuat",
                author = "Nguyen Tuan Dung",
                publisher = "NXB Back Khoa",
                year = 2024,
                type = "IT",
                quantities = 3
            )
        )
    }
}