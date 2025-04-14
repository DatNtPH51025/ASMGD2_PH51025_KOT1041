package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           ManHinhHoSo()
        }
    }
}

data class MucHoSo(val tieuDe: String, val moTa: String)

@Preview
@Composable
fun ManHinhHoSo() {
    // Danh sách các mục trong hồ sơ
    val danhSachMucHoSo = listOf(
        MucHoSo("Đơn Hàng Của Tôi", "Đã có 10 đơn hàng"),
        MucHoSo("Địa Chỉ Giao Hàng", "03 Địa chỉ"),
        MucHoSo("Phương Thức Thanh Toán", "Bạn có 2 thẻ"),
        MucHoSo("Nhận Xét Của Tôi", "Nhận xét cho 5 sản phẩm"),
        MucHoSo("Cài Đặt", "Thông báo, Mật khẩu, FAQ, Liên hệ")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Thanh tìm kiếm và tiêu đề
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Tìm kiếm",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Hồ Sơ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = "Giỏ hàng",
                modifier = Modifier.size(24.dp)
            )
        }

        // Thông tin người dùng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_bruno),
                contentDescription = "Ảnh đại diện",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Bruno Pham",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "bruno203@gmail.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // Danh sách các mục
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(danhSachMucHoSo) { muc ->
                CardMucHoSo(muc)
            }
        }
    }
}

@Composable
fun CardMucHoSo(muc: MucHoSo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Xử lý khi nhấn vào mục */ },
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = muc.tieuDe,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = muc.moTa,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Mũi tên",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}