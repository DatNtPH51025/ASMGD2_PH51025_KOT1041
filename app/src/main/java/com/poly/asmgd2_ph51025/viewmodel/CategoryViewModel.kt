package com.poly.asmgd2_ph51025.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poly.asmgd2_ph51025.models.Category
import com.poly.asmgd2_ph51025.models.toCategory
import com.poly.asmgd2_ph51025.service.RetrofitService
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private val lstCategory = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = lstCategory

    init {
        getCategory()
    }

    fun getCategory() {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.getCategory()
                if (response.isSuccessful) {
                    lstCategory.postValue(response.body()?.map { it.toCategory() })
                } else {
                    lstCategory.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "Get Category Error: " + e.message)
                lstCategory.postValue(emptyList())
            }
        }
    }
    fun addCategory(category: Category) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.addCategory(category)
                if (response.isSuccessful) {
                    getCategory()
                    Log.e("TAG", "Add Category success: " + response.body())
                } else {
                    Log.e("TAG", "Add Category Error: " + response.message())
                    false
                }
            } catch (e: Exception) {
                Log.e("TAG", "Get Category Error: " + e.message)

            }
        }
    }

    fun deleteCategory(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.deleteCategory(id)
                if (response.isSuccessful) {
                    getCategory()
                    Log.e("TAG", "Delete Category success: " + response.body())
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("TAG", "Get Category Error: " + e.message)
            }
        }
    }

    fun updateCategory(id: String, category: Category) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().productService.updateCategory(id, category)

                if (response.isSuccessful) {
                    getCategory()
                    Log.e("TAG", "Update Category success: " + response.body())
                } else {
                    Log.e("TAG", "Update Category Error: " + response.message())
                    false
                }
            } catch (e: Exception) {
                Log.e("TAG", "Get Category Error: " + e.message)
            }
        }
    }

}