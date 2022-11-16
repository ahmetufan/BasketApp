package com.ahmet.basket.retrofit

import com.ahmet.basket.models.Product
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(private val apiService: ApiService) {

    fun getProduct() : Single<List<Product>> {
        return apiService.getProduct()
    }
}