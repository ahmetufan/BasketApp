package com.ahmet.basket.retrofit

import com.ahmet.basket.models.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

//    https://nonchalant-fang.glitch.me/ listing

    @GET("listing")
    fun getProduct(): Single<List<Product>>
}