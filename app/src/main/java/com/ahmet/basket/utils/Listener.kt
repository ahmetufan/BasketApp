package com.ahmet.basket.utils

import com.ahmet.basket.models.Product

interface Listener {
    fun onItemClick(product: Product)
}