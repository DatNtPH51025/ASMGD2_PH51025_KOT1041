package com.poly.asmgd2_ph51025.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

open class RetrofitService() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.22:3000/" )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}