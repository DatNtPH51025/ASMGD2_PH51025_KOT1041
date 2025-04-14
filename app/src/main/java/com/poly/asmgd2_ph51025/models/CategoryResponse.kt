package com.poly.asmgd2_ph51025.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
fun CategoryResponse.toCategory(): Category {
    return Category(
        id = this.id,
        name = this.name
    )
}