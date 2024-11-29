package com.example.librarymanagement.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor

@Composable
fun AddNewBookScreen() {
    Scaffold(
        topBar = { AddAppBar(title = "Thêm sách") }
    ) { innerPadding ->
        AddNewBook(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun AddNewBook(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
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
        AddInfo(label = "Tên sách", modifier = Modifier.fillMaxWidth())
        AddInfo(label = "Tác giả", modifier = Modifier.fillMaxWidth())
        AddInfo(label = "Nhà xuất bản", modifier = Modifier.fillMaxWidth())
        Row {
            AddInfo(label = "Năm xuất bản", modifier = Modifier.width(120.dp))
            Spacer(modifier = Modifier.weight(1f))
            AddInfo(label = "Thể loại", modifier = Modifier.width(200.dp))
        }
        AddInfo(label = "Số lượng", modifier = Modifier.width(120.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
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

@Preview(showBackground = true)
@Composable
fun AddNewBookScreenPreview() {
    LibraryManagementTheme { AddNewBookScreen() }
}