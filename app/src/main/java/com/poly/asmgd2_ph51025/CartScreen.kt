package com.poly.asmgd2_ph51025

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

class CartScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhGioHang()
        }
    }
}

data class SanPhamGioHang(val ten: String, val gia: Double, val hinhAnh: Int, var soLuong: Int)

@Preview
@SuppressLint("UnrememberedMutableState")
@Composable
fun ManHinhGioHang() {
    // Danh sách sản phẩm trong giỏ hàng
    val danhSachGioHang = remember {
        mutableStateListOf(
            SanPhamGioHang("Tủ Nhỏ", 25.00, R.drawable.img_lamp_simple, 1),
            SanPhamGioHang("Bàn Cà Phê", 20.00, R.drawable.img_minimal_stand, 1),
            SanPhamGioHang("Bàn Đơn Giản", 50.00, R.drawable.img_lamp_simple, 1)
        )
    }

    // Tính tổng giá
    val tongGia by derivedStateOf {
        danhSachGioHang.sumOf { it.gia * it.soLuong }
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Trường nhập mã khuyến mãi
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Xử lý nhập mã khuyến mãi */ },
                    label = { Text("Nhập mã khuyến mãi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "Áp dụng mã",
                            modifier = Modifier.clickable { /* Xử lý áp dụng mã */ }
                        )
                    }
                )

                // Tổng giá
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TỔNG:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${String.format("%.2f", tongGia)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Nút Thanh Toán
                Button(
                    onClick = { /* Xử lý thanh toán */ },
                    colors = ButtonDefaults.buttonColors( Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Thanh Toán",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
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
                    text = "Giỏ Hàng",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(24.dp)) // Để cân bằng layout
            }

            // Danh sách sản phẩm trong giỏ
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(danhSachGioHang) { sanPham ->
                    CardSanPhamGioHang(
                        sanPham = sanPham,
                        onXoa = { danhSachGioHang.remove(sanPham) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardSanPhamGioHang(sanPham: SanPhamGioHang, onXoa: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hình ảnh sản phẩm
            Image(
                painter = painterResource(id = sanPham.hinhAnh),
                contentDescription = sanPham.ten,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            // Thông tin sản phẩm
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = sanPham.ten,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$${String.format("%.2f", sanPham.gia)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Bộ chọn số lượng
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    IconButton(onClick = { if (sanPham.soLuong > 1) sanPham.soLuong-- }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "Giảm số lượng",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = sanPham.soLuong.toString().padStart(2, '0'),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { sanPham.soLuong++ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Tăng số lượng",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Nút xóa
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Xóa",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onXoa() }
            )
        }
    }
}