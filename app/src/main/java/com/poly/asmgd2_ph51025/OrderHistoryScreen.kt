package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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

class OrderHistoryScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhDonHang()
        }
    }
}

data class DonHang(
    val maDonHang: String,
    val ngay: String,
    val soLuong: Int,
    val tongTien: Int,
    val trangThai: String
)

@Preview
@Composable
fun ManHinhDonHang() {
    // Danh sách đơn hàng mẫu
    val danhSachDonHang = listOf(
        DonHang("Order No238562312", "20/03/2020", 3, 150, "Delivered"),
        DonHang("Order No238562312", "20/03/2020", 3, 150, "Delivered"),
        DonHang("Order No238562312", "20/03/2020", 3, 150, "Delivered")
    )

    // Trạng thái tab được chọn
    var tabChon by remember { mutableStateOf("Delivered") }
    val tabs = listOf("Delivered", "Processing", "Canceled")

    Scaffold(
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
                    text = "Đơn Hàng Của Tôi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(24.dp)) // Để cân bằng layout
            }

            // Tabs trạng thái đơn hàng
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tabs.forEach { tab ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { tabChon = tab },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tab,
                            fontSize = 16.sp,
                            color = if (tabChon == tab) Color.Black else Color.Gray,
                            fontWeight = if (tabChon == tab) FontWeight.Bold else FontWeight.Normal
                        )
                        if (tabChon == tab) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(30.dp)
                                    .background(Color.Black)
                            )
                        }
                    }
                }
            }

            // Danh sách đơn hàng
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(danhSachDonHang.filter { it.trangThai == tabChon }) { donHang ->
                    CardDonHang(donHang)
                }
            }
        }
    }
}

@Composable
fun CardDonHang(donHang: DonHang) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = donHang.maDonHang,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = donHang.ngay,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Số lượng: ${donHang.soLuong.toString().padStart(2, '0')}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Tổng tiền: $${donHang.tongTien}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Xử lý xem chi tiết */ },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)
                ) {
                    Text(
                        text = "Chi Tiết",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = donHang.trangThai,
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50), // Màu xanh cho trạng thái "Delivered"
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}