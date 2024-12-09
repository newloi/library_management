package com.example.librarymanagement.ui.member

import com.example.librarymanagement.ui.navigation.NavigationDestination
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.Member
import com.example.librarymanagement.ui.AddAppBar
import com.example.librarymanagement.ui.GenderDropList
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor

object MemberEditDestination : NavigationDestination{
    override val route = "member_edit"
//    override val title = ""
}
@Composable
fun MemberEditScreen(
    @DrawableRes memberImage: Int =  R.drawable.book,
    member: Member,
    modifier: Modifier = Modifier,
    //navigateToEditMember: () -> Unit,
    //navigateBack: () -> Unit
) {
    Scaffold(
        topBar = { AddAppBar(title = "Thanh vien 00001", navigateBack = {}) },
        floatingActionButton = {
            Button(
                onClick = {},
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                modifier = Modifier.size(100.dp, 40.dp)
            ) {
                Text(
                    text = "Xong",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    ) { innerPadding ->
        MemberEdit(
            memberImage = memberImage,
            member = member,
            modifier.padding(innerPadding))
    }
}

@Composable
private fun MemberEdit(
    @DrawableRes memberImage: Int,
    member: Member,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .padding(32.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ){ focusManager.clearFocus() }
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
            canEdit = true
        )
        Row {
            GenderDropList("Nam", modifier = Modifier.width(108.dp))
            Spacer(modifier = modifier.width(32.dp))
            InfoAbout(
                label = "Ngày sinh",
                value = member.dateOfBirth,
                canEdit = true,
                modifier = Modifier.weight(1.5f)
            )
        }
        InfoAbout(
            label = "Địa chỉ",
            value = member.address,
            canEdit = true
        )
        InfoAbout(
            label = "Ngày đăng kí",
            value = member.registrationDate,
            canEdit = true
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MemberEditPreview(){
    val member1: Member = Member(
        id = 0,
        name = "Lưu Ngọc Lợi",
        gender = "Nam",
        dateOfBirth = "24/05/2004",
        address = "Hưng Yên",
        registrationDate = "22/11/2024"
    )
    LibraryManagementTheme {
        MemberEditScreen(memberImage = R.drawable.lamda_image, member = member1)
    }

}
