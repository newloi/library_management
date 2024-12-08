package com.example.librarymanagement.ui.member

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.AddInfo
import com.example.librarymanagement.ui.DropList
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor

object AddNewMemberDestination : NavigationDestination {
    override val route = "add_new_member"
    override val title = ""
}

@Composable
fun AddNewMemberScreen() {
    Scaffold(
        topBar = { AddAppBar(title = "Thêm thành viên") }
    ) { innerPadding ->
        AddNewMember(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun AddNewMember(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
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
                    onClick = {},
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
        AddInfo(label = "Họ và tên", modifier = Modifier.fillMaxWidth())
        Row {
            DropList(label = "Giới tính", items = listOf("Nam", "Nữ", "Khác"), modifier = Modifier.width(108.dp))
            Spacer(modifier = Modifier.width(16.dp))
            AddInfo(label = "Ngày sinh", modifier = Modifier.width(200.dp))
        }
        AddInfo(label = "Địa chỉ", modifier = Modifier.fillMaxWidth())
        AddInfo(label = "Họ và tên", modifier = Modifier.fillMaxWidth())
        Button(
            onClick = {},
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

@Preview(showBackground = true)
@Composable
fun AddNewMemberScreenPreview() {
    LibraryManagementTheme { AddNewMemberScreen() }
}