package com.example.librarymanagement.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Member
import com.example.librarymanagement.ui.theme.LibraryManagementTheme

@Composable
private fun MemberDetail(
    @DrawableRes memberImage: Int,
    member: Member,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ảnh",
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(memberImage),
            contentDescription = null,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Thông tin thành viên",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        InfoAbout(
            label = "Họ và tên",
            value = member.name,
            modifier = modifier.fillMaxWidth()
        )
        Row {
            InfoAbout(
                label = "Giới tính",
                value = member.gender,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.width(32.dp))
            InfoAbout(
                label = "Ngày sinh",
                value = member.dateOfBirth,
                modifier = modifier.fillMaxWidth()
            )
        }
        InfoAbout(
            label = "Địa chỉ",
            value = member.address,
            modifier = modifier.weight(1f)
        )
        InfoAbout(
            label = "Ngày đăng kí",
            value = member.registrationDate,
            modifier = modifier.weight(2f)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MemberDetailPreview() {
    LibraryManagementTheme {
        MemberDetail(
            memberImage = R.drawable.book,
            member = Member(
                name = "Lưu Ngọc Lợi",
                gender = "Nam",
                dateOfBirth = "24/05/2004",
                address = "Hưng Yên",
                registrationDate = "22/11/2024"
            )
        )
    }
}