package com.poly.asmgd2_ph51025.service


import com.poly.asmgd2_ph51025.models.Category
import com.poly.asmgd2_ph51025.models.CategoryResponse
import com.poly.asmgd2_ph51025.models.Product
import com.poly.asmgd2_ph51025.models.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getLists(): Response<List<ProductResponse>>

    @GET("products/{id}")
    suspend fun getDetailProduct(@Path("id") id: String): Response<ProductResponse>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Unit>

    @POST("products")
    suspend fun addProduct(@Body product: Product): Response<Unit>

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product): Response<Unit>

    @GET("category")
    suspend fun getCategory(): Response<List<CategoryResponse>>

    @DELETE("category/{id}")
    suspend fun deleteCategory(@Path("id") id: String): Response<Unit>

    @POST("category")
    suspend fun addCategory(@Body category: Category): Response<Unit>

    @PUT("category/{id}")
    suspend fun updateCategory(@Path("id") id: String, @Body category: Category): Response<Unit>

}