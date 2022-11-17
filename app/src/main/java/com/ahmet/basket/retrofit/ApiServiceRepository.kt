package com.ahmet.basket.retrofit

import android.util.Log
import com.ahmet.basket.models.Post
import com.ahmet.basket.models.PostCevap
import com.ahmet.basket.models.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(private val apiService: ApiService) {

    fun getProduct() : Single<List<Product>> {
        return apiService.getProduct()
    }

    suspend fun pushPost(post: List<Post>): Response<PostCevap> {
        return apiService.pushPost(post)
    }


}