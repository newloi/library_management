package com.example.librarymanagement.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.librarymanagement.ui.TextFieldAbout
import com.example.librarymanagement.ui.theme.LoginBackground
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Roboto


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
                painter = painterResource(0),
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
            TextFieldAbout(label = "Mật khẩu", icon = 0)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Căn lề phải trong Row
            ) {
                OutlinedButton(
                    onClick = {/*TODO*/ },
                    border = null,
                    modifier = Modifier.padding(vertical = 1.dp)
                ) {
                    Text(
                        text = "Quên mật khẩu",
                        fontSize = 13.sp,
                        fontFamily = Roboto
                        )
                }
            }

            Button(
                onClick = {/*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)

            ) {
                Text(
                    text= "Đăng nhập",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(4.dp))

            var rememberMe by remember { mutableStateOf(true) } // Trạng thái của checkbox
            // Checkbox và Ghi nhớ thông tin
            Row(
                verticalAlignment = Alignment.CenterVertically, // Căn giữa checkbox và văn bản
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black, // Màu checkbox khi được chọn
                        uncheckedColor = Color.Gray, // Màu checkbox khi chưa được chọn
                        checkmarkColor = Color.White, // Màu dấu tích bên trong

                    ) ,
                    modifier = Modifier.size(16.dp)
                )



                Text(
                    text = "Ghi nhớ thông tin đăng nhập", // "Ghi nhớ thông tin đăng nhập"
                    fontSize = 13.sp,
                    fontFamily = Roboto,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top= 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically // Căn lề giua trong Row)
            ) {
                Text(
                    text = "Chưa có tài khoản?",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(end = 0.dp)

                )
                OutlinedButton(
                    onClick = {/*TODO*/ },
                    border = null,
                    modifier = Modifier.padding(start = 0.dp),


                ) {
                    Text(
                        text = "Đăng ký ngay",
                        style = MaterialTheme.typography.labelMedium


                    )
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
