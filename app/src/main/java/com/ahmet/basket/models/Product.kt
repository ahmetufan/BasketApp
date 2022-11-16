package com.ahmet.basket.models

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val currency: String,
    val image: String
) {
    var count=0
}