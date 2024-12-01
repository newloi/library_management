package com.example.librarymanagement.ui.member

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Member
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.LibraryManagementTheme

object MemberDetailDestination : NavigationDestination {
    override val route = "member_detail"
    override val title = ""
}

@Composable
fun MemberDetailScreen(
    @DrawableRes memberImage: Int,
    member: Member,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { InfoAppBar(title = stringResource(R.string.thanh_vien, member.id)) }
    ) { innerPadding ->
        MemberDetail(memberImage = memberImage, member = member, modifier.padding(innerPadding))
    }
}

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
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Ảnh",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )
        Image(
            painter = painterResource(memberImage),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(117.dp, 140.dp)
        )
        Text(
            text = "Thông tin thành viên",
            style = MaterialTheme.typography.titleLarge
        )
        InfoAbout(
            label = "Họ và tên",
            value = member.name,
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            InfoAbout(
                label = "Giới tính",
                value = member.gender,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = modifier.width(32.dp))
            InfoAbout(
                label = "Ngày sinh",
                value = member.dateOfBirth,
                modifier = Modifier.weight(1.5f)
            )
        }
        InfoAbout(
            label = "Địa chỉ",
            value = member.address,
            modifier = Modifier.fillMaxWidth()
        )
        InfoAbout(
            label = "Ngày đăng kí",
            value = member.registrationDate,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MemberDetailPreview() {
    LibraryManagementTheme {
        MemberDetailScreen(
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