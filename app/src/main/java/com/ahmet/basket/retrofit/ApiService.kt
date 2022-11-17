package com.ahmet.basket.retrofit

import com.ahmet.basket.models.Post
import com.ahmet.basket.models.PostCevap
import com.ahmet.basket.models.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

//    https://nonchalant-fang.glitch.me/ listing
//    https://nonchalant-fang.glitch.me/ order

    @GET("listing")
    fun getProduct(): Single<List<Product>>

    @POST("order")
    suspend fun pushPost(
        @Body post :List<Post>
    ) : Response<PostCevap>

}