package com.poly.asmgd2_ph51025.models

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Float,
    @SerializedName("description") val description: String,
    @SerializedName("idCate") val idCate: String,
)

fun ProductResponse.toProduct(): Product {
    return Product (
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price.toDouble(),
        description = this.description,
        idCate = this.idCate
    )
}
