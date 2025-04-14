package com.poly.asmgd2_ph51025

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poly.asmgd2_ph51025.models.Category
import com.poly.asmgd2_ph51025.viewmodel.CategoryViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel, navController: NavController) {
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
    ) {padding ->
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
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Tìm kiếm",
                    modifier = Modifier.size(24.dp)
                    .clickable { navController.popBackStack() }
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


            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(lstCategory?: emptyList()) {
                        product ->
                    CardCategory(product, navController, categoryViewModel, snackbarHostState, coroutineScope)
                }
            }
        }
        if (showDialog) {
            DialogThemDanhMuc(
                onDismiss = { showDialog = false },
                onConfirm = { name, ->
                    // Tạo sản phẩm mới và thêm vào ViewModel
                    val newCategory = Category(
                        id = ((lstCategory?.size ?: 0) + 1).toString(), // Tạo ID tạm thời
                        name = name,
                    )
                    categoryViewModel.addCategory(newCategory)
                    showDialog = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Thêm danh mục thành công!")
                    }
                },
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
    }
}

@Composable
fun DialogThemDanhMuc(
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Thêm Danh Mục Mới") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên danh mục") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập tên danh mục!")
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
fun CardCategory(
    category: Category,
    navController: NavController,
    viewModel: CategoryViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var showDialogDelete by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
        .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),) {
        Row(
            modifier = Modifier
                .padding(16.dp).
                fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Row {
                Icon(
                    Icons.Filled.Edit,
                    tint = Color.Blue,
                    contentDescription = "Thêm vào giỏ hàng",
                    modifier = Modifier.size(24.dp)
                        .clickable { showEditDialog = true }
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
                Text(text = "Bạn có chắc chắn muốn xóa danh mục ${category.name}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Xóa sản phẩm khi người dùng xác nhận
                        viewModel.deleteCategory(category.id)
                        showDialogDelete = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Xóa danh mục thành công!")
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
            category = category,
            onDismiss = { showEditDialog = false },
            onConfirm = { name ->
                // Cập nhật thông tin sản phẩm khi người dùng xác nhận
                val updatedCategory = category.copy(
                    name = name
                )
                viewModel.updateCategory(category.id, updatedCategory)
                showEditDialog = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Cập nhật danh mục thành công!",
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
fun DialogSuaSanPham(
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var name by remember { mutableStateOf(category.name) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Chỉnh Sửa Danh Mục") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên danh mục") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() ) {
                        onConfirm(name)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập tên danh mục!")
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