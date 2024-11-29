package com.example.librarymanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.LoginBackground
import com.example.librarymanagement.ui.theme.MainColor


@Composable
fun RegisterScreen (modifier: Modifier = Modifier){
    Surface(color = LoginBackground, modifier = modifier.fillMaxSize() ) {
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo"
            )

            Spacer(modifier = Modifier.padding(10.dp))

            TextFieldAbout(label = "Nhập email của bạn")
            TextFieldAbout(label = "Tên đăng nhập")
            TextFieldAbout(label = "Nhập mật khẩu", icon = R.drawable.eye)
            Text(
                text= "Mật khẩu gồm 6 ký tự",
                modifier = Modifier.fillMaxWidth().padding(start= 32.dp, top= 2.dp,bottom=10.dp),
                textAlign = TextAlign.Start,

                )

            Button(
                onClick = { },
                border = null,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor)
            ) {
                Text(text="Đăng ký")


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    LibraryManagementTheme { RegisterScreen() }
}

