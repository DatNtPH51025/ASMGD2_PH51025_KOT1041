package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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


class NotificationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManHinhThongBao()
        }
    }
}

data class ThongBao(
    val tieuDe: String,
    val moTa: String,
    val hinhAnh: Int,
    val nhan: String? = null, // Nhãn như "New", "HOT!", hoặc null (cho chấm đỏ)
    val coChamDo: Boolean = false // Có chấm đỏ hay không
)

@Preview
@Composable
fun ManHinhThongBao() {
    // Danh sách thông báo mẫu
    val danhSachThongBao = listOf(
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã được xác nhận",
            moTa = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
            hinhAnh = R.drawable.img_lamp_simple,
            nhan = "New"
        ),
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã bị hủy",
            moTa = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
            hinhAnh = R.drawable.img_minimal_stand
        ),
        ThongBao(
            tieuDe = "Khám phá các món đồ nội thất giảm giá mạnh trong tuần này.",
            moTa = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
            hinhAnh = R.drawable.img_lamp_simple,
            nhan = "HOT!"
        ),
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã được vận chuyển thành công",
            moTa = "Vui lòng giúp chúng tôi xác nhận và đánh giá đơn hàng để nhận mã giảm giá 10% cho đơn hàng tiếp theo.",
            hinhAnh = R.drawable.img_minimal_stand,
            coChamDo = true
        ),
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã được xác nhận",
            moTa = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
            hinhAnh = R.drawable.img_lamp_simple
        ),
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã bị hủy",
            moTa = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
            hinhAnh = R.drawable.img_minimal_stand
        ),
        ThongBao(
            tieuDe = "Đơn hàng #123456789 của bạn đã được vận chuyển thành công",
            moTa = "Vui lòng giúp chúng tôi xác nhận và đánh giá đơn hàng để nhận mã giảm giá 10% cho đơn hàng tiếp theo.",
            hinhAnh = R.drawable.img_lamp_simple,
            coChamDo = true
        )
    )

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Tìm kiếm",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Thông Báo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(24.dp)) // Để cân bằng layout
            }

            // Danh sách thông báo
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(danhSachThongBao) { thongBao ->
                    CardThongBao(thongBao)
                }
            }
        }
    }
}

@Composable
fun CardThongBao(thongBao: ThongBao) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hình ảnh
            Image(
                painter = painterResource(id = thongBao.hinhAnh),
                contentDescription = thongBao.tieuDe,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            // Thông tin thông báo
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = thongBao.tieuDe,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    if (thongBao.nhan != null) {
                        Text(
                            text = thongBao.nhan,
                            fontSize = 12.sp,
                            color = if (thongBao.nhan == "New") Color.Green else Color.Red,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    } else if (thongBao.coChamDo) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Red, CircleShape)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = thongBao.moTa,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}