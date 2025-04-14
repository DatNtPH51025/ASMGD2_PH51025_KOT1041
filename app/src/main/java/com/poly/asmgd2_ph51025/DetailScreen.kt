package com.poly.asmgd2_ph51025

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.poly.asmgd2_ph51025.viewmodel.ProductViewModel

class DetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ManHinhChiTietSanPham()
        }
    }
}

//@Preview
@Composable
fun ManHinhChiTietSanPham(id: String?, navController: NavController) {
    var soLuong by remember { mutableStateOf(1) }

    val productViewModel: ProductViewModel = viewModel()
    productViewModel.getDetail(id.toString())
    val product by productViewModel.productDetail.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp)
    ) {
        // Hình ảnh sản phẩm và các nút điều hướng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = product?.image,
                contentDescription = "Hình ảnh sản phẩm",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )

            // Nút quay lại
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Quay lại",
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, CircleShape)
                    .padding(5.dp)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .clickable { navController.popBackStack() }
            )

            // Nút dấu trang
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = "Dấu trang",
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, CircleShape)
                    .padding(5.dp)
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable { /* Xử lý dấu trang */ }
            )

            // Bộ chọn màu (giả lập)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterStart)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                            .background(
                                if (it == 0) Color(0xFF8B4513) else if (it == 1) Color(0xFFF5F5DC) else Color.Gray,
                                CircleShape
                            )
                    )
                }
            }
        }

        // Nội dung chi tiết sản phẩm
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Tên sản phẩm
            Text(
                text = "${product?.name}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Giá và bộ chọn số lượng
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${product?.price}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (soLuong > 1) soLuong-- }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "Giảm số lượng",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = soLuong.toString().padStart(2, '0'),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { soLuong++ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Tăng số lượng",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Đánh giá
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Đánh giá",
                    tint = Color(0xFFFFD700), // Màu vàng cho ngôi sao
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "4.5 (50 đánh giá)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Mô tả sản phẩm
            Text(
                text = "${product?.description}",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Nút Thêm vào giỏ hàng
            Button(
                onClick = { /* Xử lý thêm vào giỏ hàng */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Thêm vào giỏ hàng",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}