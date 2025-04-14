package com.poly.asmgd2_ph51025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.poly.asmgd2_ph51025.models.Product
import com.poly.asmgd2_ph51025.viewmodel.CategoryViewModel
import com.poly.asmgd2_ph51025.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text


data class BottomNavItem(
    val title: String,
    val icon: Int,
    val id: String
)

class HomeScreen : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route ?: "home"

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController = navController, currentRoute = currentRoute)
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") {
                        ManHinhSanPham(productViewModel, categoryViewModel, navController)
                    }
                    composable("detail/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id") ?: ""
                        ManHinhChiTietSanPham(id, navController)
                    }
                    composable("category") {
                        CategoryScreen(categoryViewModel, navController)
                    }

                }
            }
        }

    }
}
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String
) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "home"),
        BottomNavItem("Favorites", R.drawable.favorite, "favorites"),
        BottomNavItem("Notification", R.drawable.bell, "notification"),
        BottomNavItem("Profile", R.drawable.person, "profile")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.id,
                onClick = {
                    if (currentRoute != item.id) {
                        navController.navigate(item.id) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun ManHinhSanPham(viewModel: ProductViewModel, categoryViewModel: CategoryViewModel, navController: NavController) {
    val lstProduct by viewModel.products.observeAsState()
    val lstCategory by categoryViewModel.categories.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {

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
                    text = "Make home\n" +
                            "BEAUTIFUL",
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

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                lstCategory?.forEach { danhMuc ->
                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { navController.navigate("category") }
//                            .background(if (danhMuc == "Phổ Biến") Color.Black else Color.LightGray)
                        ,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = danhMuc.name,
                            color = if (danhMuc.name == "Bàn") Color.White else Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            // Danh sách sản phẩm dạng lưới
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lstProduct?: emptyList()) {
                    product ->
                    CardSanPham(product, navController, viewModel, snackbarHostState, coroutineScope)
                }
            }
        }
        if (showDialog) {
            DialogThemSanPham(
                onDismiss = { showDialog = false },
                onConfirm = { name, price, image, description ->
                    // Tạo sản phẩm mới và thêm vào ViewModel
                    val newProduct = Product(
                        id = ((lstProduct?.size ?: 0) + 1).toString(), // Tạo ID tạm thời
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0,
                        image = image,
                        description = description,
                        idCate = "1"
                    )
                    viewModel.addProduct(newProduct)
                    showDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Thêm sản phẩm thành công!")
                    }
                },
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
    }
}

@Composable
fun CardSanPham(
    product: Product,
    navController: NavController,
    viewModel: ProductViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    var showDialogDelete by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val id = product.id
                viewModel.getDetail(product.id)
                navController.navigate("detail/${id}")
            },
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            // Hình ảnh sản phẩm
            AsyncImage(
                model = product.image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )

            // Thông tin sản phẩm
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        Icons.Filled.Edit,
                        tint = Color.Blue,
                        contentDescription = "Thêm vào giỏ hàng",
                        modifier = Modifier.size(24.dp)
                            .clickable { showEditDialog = true }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.price.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        Icons.Filled.Delete,
                        tint = Color.Red,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                            .clickable {
                                // Hiển thị dialog khi nhấn nút xóa
                                showDialogDelete = true
                            }
                    )
                }
            }
        }
    }
    if (showDialogDelete) {
        AlertDialog(
            onDismissRequest = {
                // Đóng dialog khi người dùng nhấn ra ngoài
                showDialogDelete = false
            },
            title = {
                Text(text = "Xác Nhận Xóa")
            },
            text = {
                Text(text = "Bạn có chắc chắn muốn xóa sản phẩm ${product.name}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Xóa sản phẩm khi người dùng xác nhận
                        viewModel.deleteProduct(product.id)
                        showDialogDelete = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Xóa sản phẩm thành công!")
                        }
                    }
                ) {
                    Text("Xác nhận", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Đóng dialog khi người dùng hủy
                        showDialogDelete = false
                    }
                ) {
                    Text("Hủy", color = Color.Black)
                }
            }
        )
    }
    if (showEditDialog) {
        DialogSuaSanPham(
            product = product,
            onDismiss = { showEditDialog = false },
            onConfirm = { name, price, image, description ->
                // Cập nhật thông tin sản phẩm khi người dùng xác nhận
                val updatedProduct = product.copy(
                    name = name,
                    price = price.toDoubleOrNull() ?: product.price,
                    image = image,
                    description = description
                )
                viewModel.updateProduct(product.id, updatedProduct)
                showEditDialog = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Cập nhật sản phẩm thành công!",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }

}

@Composable
fun DialogThemSanPham(
    onDismiss: () -> Unit,
    onConfirm: (name: String, price: String, image: String, description: String) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    // Trạng thái cho các trường nhập liệu
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Thêm Sản Phẩm Mới") },
        text = {
            Column {
                if (image.isNotBlank()) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 10.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(80.dp))
                            .align(Alignment.CenterHorizontally),
                        error = painterResource(R.drawable.img_logo)
                    )
                } else {
                    AsyncImage(
                        model = R.drawable.img_logo,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 10.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(80.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Giá") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("URL hình ảnh") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && price.isNotBlank() && image.isNotBlank()) {
                        onConfirm(name, price, image, description)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập đầy đủ thông tin!")
                        }
                    }
                }
            ) {
                Text("Xác nhận", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Hủy", color = Color.Black)
            }
        }
    )
}

@Composable
fun DialogSuaSanPham(
    product: Product,
    onDismiss: () -> Unit,
    onConfirm: (name: String, price: String, image: String, description: String) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var image by remember { mutableStateOf(product.image) }
    var description by remember { mutableStateOf(product.description) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Chỉnh Sửa Sản Phẩm") },
        text = {
            Column {
                if (image.isNotBlank()) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 10.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(80.dp))
                            .align(Alignment.CenterHorizontally),
                        error = painterResource(R.drawable.img_logo)
                    )
                } else {
                    AsyncImage(
                        model = R.drawable.img_logo,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 10.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(80.dp))
                            .align(Alignment.CenterHorizontally),
                    )
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Giá") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("URL hình ảnh") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && price.isNotBlank() && image.isNotBlank()) {
                        onConfirm(name, price, image, description)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập đầy đủ thông tin!")
                        }
                    }
                }
            ) {
                Text("Xác nhận", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Hủy", color = Color.Black)
            }
        }
    )
}