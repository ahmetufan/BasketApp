package com.ahmet.basket.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

    val id: Int,
    val name: String,
    val price: String,
    val currency: String,
    val image: String
): Serializable {
    var count=0
}