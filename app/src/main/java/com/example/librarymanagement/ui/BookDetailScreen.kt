package com.example.librarymanagement.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun BookDetailScreen(modifier: Modifier = Modifier) {

}

@Composable
private fun BookDetail(
    @DrawableRes bookImage: Int,
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        Text(
            text = "Ảnh",
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(bookImage),
            contentDescription = null,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Thông tin sách",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        InfoAbout(label = "Tên", value = book.name, modifier = modifier.fillMaxWidth())
        InfoAbout(label = "Tác giả", value = book.author, modifier = modifier.fillMaxWidth())
        InfoAbout(label = "Nhà xuất bản", value = book.publisher, modifier = modifier.fillMaxWidth())
        Row(modifier = modifier) {
            InfoAbout(label = "Năm", value = book.year.toString(), modifier = modifier.weight(1f))
            Spacer(modifier = modifier.width(32.dp))
            InfoAbout(label = "Thể loại", value = book.type, modifier = modifier.weight(2f))
        }
        InfoAbout(label = "Số lượng", value = book.quantities.toString(), modifier = modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InfoAbout(
    label: String,
    value: String,
    modifier:Modifier = Modifier
) {
    OutlinedTextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = value,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        ),
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BookDetailBodyPreview() {
    LibraryManagementTheme {
        BookDetail(
            bookImage = R.drawable.book,
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