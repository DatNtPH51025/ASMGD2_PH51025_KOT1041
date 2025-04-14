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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

class ReviewScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhDanhGia()
        }
    }
}


data class NhanXet(
    val tenNguoiDung: String,
    val ngay: String,
    val soSao: Int,
    val noiDung: String,
    val hinhDaiDien: Int
)

@Preview
@Composable
fun ManHinhDanhGia() {
    // Danh sách nhận xét mẫu
    val danhSachNhanXet = listOf(
        NhanXet(
            tenNguoiDung = "Bruno Fernandes",
            ngay = "20/03/2020",
            soSao = 5,
            noiDung = "Đồ nội thất đẹp với giao hàng tốt. Thời gian giao hàng rất nhanh. Sản phẩm trông giống hệt hình ảnh trong ứng dụng. Bên cạnh đó, màu sắc cũng giống và chất lượng rất tốt dù giá rẻ.",
            hinhDaiDien = R.drawable.avatar_bruno
        ),
        NhanXet(
            tenNguoiDung = "Tracy Mosby",
            ngay = "20/03/2020",
            soSao = 4,
            noiDung = "Đồ nội thất đẹp với giao hàng tốt. Thời gian giao hàng rất nhanh. Sản phẩm trông giống hệt hình ảnh trong ứng dụng. Bên cạnh đó, màu sắc cũng giống và chất lượng rất tốt dù giá rẻ.",
            hinhDaiDien = R.drawable.avatar_bruno
        )
    )

    Scaffold(
        bottomBar = {
            Button(
                onClick = { /* Xử lý viết nhận xét */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Viết nhận xét",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            // Tiêu đề và nút quay lại
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Quay lại",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Xử lý quay lại */ }
                )
                Text(
                    text = "Đánh Giá & Nhận Xét",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(24.dp)) // Để cân bằng layout
            }

            // Thông tin sản phẩm
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_lamp_simple),
                    contentDescription = "Tủ Nhỏ",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Tủ Nhỏ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Đánh giá",
                            tint = Color(0xFFFFD700), // Màu vàng cho ngôi sao
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "4.5",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = "10 nhận xét",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Danh sách nhận xét
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(danhSachNhanXet) { nhanXet ->
                    CardNhanXet(nhanXet)
                }
            }
        }
    }
}

@Composable
fun CardNhanXet(nhanXet: NhanXet) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = nhanXet.hinhDaiDien),
                        contentDescription = nhanXet.tenNguoiDung,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = nhanXet.tenNguoiDung,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row {
                            repeat(nhanXet.soSao) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = "Sao",
                                    tint = Color(0xFFFFD700), // Màu vàng cho ngôi sao
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            repeat(5 - nhanXet.soSao) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = "Sao",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
                Text(
                    text = nhanXet.ngay,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nhanXet.noiDung,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}