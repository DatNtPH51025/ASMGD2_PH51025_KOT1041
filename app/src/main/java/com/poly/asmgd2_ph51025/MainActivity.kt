package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                ManHinhChinh()
        }
    }
}

@Preview
@Composable
fun ManHinhChinh() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray) // Màu nền dự phòng
    ) {
        // Hình nền
        Image(
            painter = painterResource(id = R.drawable.img_background), // Thay bằng tài nguyên hình ảnh của bạn
            contentDescription = "Hình Nền",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Nội dung (Văn bản và Nút)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề
            Text(
                text = "LÀM CHO NGÔI NHÀ CỦA BẠN ĐẸP HƠN",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Phụ đề
            Text(
                text = "Nơi đơn giản nhất để bạn khám phá những món đồ nội thất tuyệt vời và làm cho ngôi nhà của bạn trở nên đẹp hơn",
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Nút Bắt Đầu
            Button(
                onClick = { /* Xử lý khi nhấn nút */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Bắt Đầu",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}