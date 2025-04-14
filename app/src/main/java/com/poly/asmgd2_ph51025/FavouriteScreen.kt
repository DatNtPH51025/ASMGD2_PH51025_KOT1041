package com.poly.asmgd2_ph51025

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

class FavouriteScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhYeuThich()
        }
    }
}

data class SanPhamYeuThich(val ten: String, val gia: String, val hinhAnh: Int)

@Preview
@Composable
fun ManHinhYeuThich() {
    // Danh sách sản phẩm yêu thích mẫu
    val danhSachYeuThich = remember {
        mutableStateListOf(
            SanPhamYeuThich("Bàn Cà Phê", "$50.00", R.drawable.img_minimal_stand),
            SanPhamYeuThich("Ghế Cà Phê", "$20.00", R.drawable.img_lamp_simple),
            SanPhamYeuThich("Tủ Nhỏ", "$25.00", R.drawable.img_minimal_stand),
            SanPhamYeuThich("Bàn Đơn Giản", "$50.00", R.drawable.img_lamp_simple),
            SanPhamYeuThich("Đèn Đơn Giản", "$12.00", R.drawable.img_minimal_stand)
        )
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = { /* Xử lý thêm tất cả vào giỏ hàng */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Thêm tất cả vào giỏ hàng",
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
            // Thanh tìm kiếm và giỏ hàng
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Tìm kiếm",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Yêu Thích",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Giỏ hàng",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Danh sách sản phẩm yêu thích
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(danhSachYeuThich) { sanPham ->
                    CardSanPhamYeuThich(
                        sanPham = sanPham,
                        onXoa = { danhSachYeuThich.remove(sanPham) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardSanPhamYeuThich(sanPham: SanPhamYeuThich, onXoa: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
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
                    text = sanPham.gia,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Nút xóa và thêm vào giỏ hàng
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Xóa",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onXoa() }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Thêm vào giỏ hàng",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Xử lý thêm vào giỏ hàng */ }
                )
            }
        }
    }
}