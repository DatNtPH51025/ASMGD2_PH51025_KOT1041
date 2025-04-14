package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Register()
        }
    }
}

@Preview
@Composable
fun Register() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Màu nền trắng
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.img_logo), // Thay bằng tài nguyên logo của bạn
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp)
            )

            // Tiêu đề
            Text(
                text = "Xin chào!",
                fontSize = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Text(
                text = "CHÀO MỪNG TRỞ LẠI",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Trường nhập liệu Email
            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Trường nhập liệu Mật khẩu
            var matKhau by remember { mutableStateOf("") }
            var hienMatKhau by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = matKhau,
                onValueChange = { matKhau = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            // Liên kết Quên mật khẩu
            Text(
                text = "Quên mật khẩu",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp)
                    .clickable { /* Xử lý quên mật khẩu */ }
            )

            // Nút Đăng nhập
            Button(
                onClick = { /* Xử lý đăng nhập */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Đăng nhập",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            // Liên kết Đăng ký
            Text(
                text = "ĐĂNG KÝ",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { /* Xử lý đăng ký */ }
            )
        }
    }
}