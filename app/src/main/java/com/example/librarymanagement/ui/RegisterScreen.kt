package com.example.librarymanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.LoginBackground
import com.example.librarymanagement.ui.theme.MainColor


@OptIn(ExperimentalMaterial3Api::class)
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

            TextFieldAbout(label = "Nhập email của bạn", modifier = Modifier.padding(bottom = 16.dp))
            TextFieldAbout(label = "Tên đăng nhập", modifier = Modifier.padding(bottom = 16.dp))
            TextFieldAbout(label = "Nhập mật khẩu", icon = R.drawable.eye)
            Text(
                text= "Mật khẩu gồm 6 ký tự",
                modifier = Modifier.fillMaxWidth().padding(start= 16.dp,bottom=8.dp, top = 4.dp),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,

                )

            TextFieldAbout(label = "Nhập lại mật khẩu",icon = R.drawable.eye)

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { },
                border = null,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.height(40.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor)

            ) {
                Text(
                    text="Đăng ký",
                    style = MaterialTheme.typography.bodyMedium
                )


            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    LibraryManagementTheme { RegisterScreen() }
}

