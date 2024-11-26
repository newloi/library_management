package com.example.librarymanagement.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.librarymanagement.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.librarymanagement.ui.theme.LoginBackground


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Surface(color = LoginBackground,
        modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 40.dp)
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo"
            )
            Text(
                text = "Đăng nhập",
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp, top = 40.dp)
            )
            TextFieldAbout(label = "Tên đăng nhập")
            Spacer(modifier = Modifier.height(10.dp))
            TextFieldAbout(label = "Mật khẩu", icon = R.drawable.eye)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Căn lề phải trong Row
            ) {
                OutlinedButton(
                    onClick = {/*TODO*/ },
                    border = null,
                    modifier = Modifier.padding(vertical = 1.dp)
                ) {
                    Text(stringResource(R.string.quen_mat_khau))
                }
            }

            Button(
                onClick = {/*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)

            ) {
                Text(stringResource(R.string.dang_nhap))
            }
            Text(
                text = stringResource(R.string.ghi_nho),
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .align(alignment = Alignment.Start)

            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top= 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically // Căn lề giua trong Row)
            ) {
                Text(
                    text = "Chưa có tài khoản?",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 0.dp)

                )
                OutlinedButton(
                    onClick = {/*TODO*/ },
                    border = null,
                    modifier = Modifier.padding(start = 0.dp)

                ) {
                    Text(
                        text = stringResource(R.string.dang_ky),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 0.dp)
                    )
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAbout(
    @DrawableRes icon: Int = 0,
    label: String,
    modifier: Modifier = Modifier) {
    TextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        value = "",
        onValueChange = {  },
        shape= RoundedCornerShape(16.dp),
        trailingIcon = { Icon(painterResource(icon), contentDescription = null) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, // Tắt đường gạch dưới khi focus
            unfocusedIndicatorColor = Color.Transparent // Tắt đường gạch dưới khi không focus
        ),
        modifier = modifier.padding(top= 8.dp, bottom = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
