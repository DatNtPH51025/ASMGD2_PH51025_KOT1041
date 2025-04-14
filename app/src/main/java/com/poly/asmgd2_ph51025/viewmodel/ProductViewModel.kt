package com.poly.asmgd2_ph51025.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poly.asmgd2_ph51025.models.Product
import com.poly.asmgd2_ph51025.models.toProduct
import com.poly.asmgd2_ph51025.service.RetrofitService

import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val lstProduct = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = lstProduct

    private val _productDetail = MutableLiveData<Product?>()
    val productDetail: LiveData<Product?> = _productDetail

    init {
        getProduct()
    }

    fun getProduct() {
        viewModelScope.launch {
            try {
                Log.d("zzz", "1 - Start getProduct()")
                val service = RetrofitService().productService
                Log.d("zzz", "2 - Got service instance")

                val response = service.getLists()
                Log.d("zzz", "Response: ${response.body()}") // Kiểm tra xem response có dữ liệu không
                Log.d("zzz", "Response code: ${response.code()}") // Kiểm tra mã HTTP của response


                Log.d("zzz", response.body().toString())
                if (response.isSuccessful) {
                    lstProduct.postValue(response.body()?.map { it.toProduct() })
                } else {
                    lstProduct.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "Get Product Error: ${e.message}", e)
                lstProduct.postValue(emptyList())
            }
        }

    }

    fun getDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.getDetailProduct(id)
                if (response.isSuccessful) {
                    _productDetail.postValue(response.body()?.toProduct())
                    Log.e("TAG","Get Product success: "+response.body()?.toProduct())
                } else {
                    _productDetail.postValue(null)
                    Log.e("TAG","Get Product Error: "+response.message())
                }
            } catch (e: Exception) {
                Log.e("TAG","Get Product Error: "+e.message)
                _productDetail.postValue(null)
            }
        }
    }

    fun deleteProduct(id: String){
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.deleteProduct(id)
                if (response.isSuccessful) {
                    getProduct()
                    Log.e("TAG","Delete Product success: "+response.body())
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("TAG","Get Product Error: "+e.message)
            }
        }
    }

    fun  addProduct(product: Product){
        viewModelScope.launch {
            Log.e("TAG","Add Product success: ")
            try {
                val response = RetrofitService().productService.addProduct(product)
                if (response.isSuccessful) {
                    getProduct()
                    Log.e("TAG","Add Product success: "+response.body())
                } else {
                    Log.e("TAG","Add Product Error: "+response.message())
                    false
                }
            } catch (e: Exception) {
                Log.e("TAG","Get Product Error: "+e.message)
                }
        }
    }

    fun updateProduct(id: String, product: Product){
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.updateProduct(id, product)
                if (response.isSuccessful) {
                    getProduct()
                    Log.e("TAG","Update Product success: "+response.body())
                } else {
                    Log.e("TAG","Update Product Error: "+response.message())
                    false
                }
                } catch (e: Exception) {
                Log.e("TAG","Get Product Error: "+e.message)
            }
        }
    }
}
