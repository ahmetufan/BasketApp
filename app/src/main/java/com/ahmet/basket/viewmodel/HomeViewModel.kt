package com.ahmet.basket.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmet.basket.models.Post
import com.ahmet.basket.models.PostCevap
import com.ahmet.basket.models.Product
import com.ahmet.basket.retrofit.ApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : ApiServiceRepository) : ViewModel() {

    private val compositeDisposable=CompositeDisposable()


    val data= MutableLiveData<List<Product>>()
    val basket= MutableLiveData<List<Product>>()
    val totalBasket=MutableLiveData<Double>()

    val myResponse=MutableLiveData<Response<PostCevap>>()



    fun getData(){
        getDataFromAPI()
    }


    private fun getDataFromAPI(){

        compositeDisposable.add(
            repository.getProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>(){
                    override fun onSuccess(t: List<Product>) {
                        data.value=t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }


    suspend fun pushPost(post: List<Post>) {
        viewModelScope.launch {
            val response=repository.pushPost(post)
            myResponse.value=response
        }
    }


    fun negatifBasket(product: Product){

        if (basket.value != null) {
            val arrayList=ArrayList(basket.value)
            if (arrayList.contains(product)) {
                val indexOfFirst=arrayList.indexOfFirst {it == product}
                val relatedProduct=arrayList.get(indexOfFirst)
                relatedProduct.count -=1
                basket.value=arrayList
            } else {

                product.count -=1
                arrayList.add(product)
                basket.value=arrayList
            }
        } else {
            val arrayList= arrayListOf(product)
            product.count -=1
            basket.value=arrayList
        }

        basket.value.let {
            refreshTotalValue(it!!)
        }
    }

    fun addToBasket(product: Product){
        if (basket.value != null) {
            val arrayList=ArrayList(basket.value)
            if (arrayList.contains(product)) {
                val indexOfFirst=arrayList.indexOfFirst {it == product}
                val relatedProduct=arrayList.get(indexOfFirst)
                relatedProduct.count +=1
                basket.value=arrayList
            } else {

                product.count +=1
                arrayList.add(product)
                basket.value=arrayList
            }
        } else {
            val arrayList= arrayListOf(product)
            product.count +=1
            basket.value=arrayList
        }

        basket.value.let {
            refreshTotalValue(it!!)
        }
    }

    private fun refreshTotalValue(listOfProduct:List<Product>) {
        var total=0.0
        listOfProduct.forEach { product ->
            val price =product.price.toDoubleOrNull()
            price?.let {
                val count=product.count
                val revenue=count * it
                total += revenue
            }
        }
        totalBasket.value=total
    }

    fun deleteProductBasket(product: Product){

        if (basket.value != null) {
            val arrayList=ArrayList(basket.value)
            arrayList.remove(product)
            basket.value=arrayList
            refreshTotalValue(arrayList)
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}