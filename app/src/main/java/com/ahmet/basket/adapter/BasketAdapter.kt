package com.ahmet.basket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.basket.databinding.RowBasketBinding
import com.ahmet.basket.models.Product
import com.ahmet.basket.utils.Listener
import com.ahmet.basket.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class BasketAdapter (val basketList:List<Product>, var viewmodel:HomeViewModel): RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    class ViewHolder(val binding:RowBasketBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowBasketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rowName.text=basketList[position].name
        holder.binding.rowCurrency.text=basketList[position].currency
        holder.binding.rowPrice.text=basketList[position].price
        holder.binding.rowCountText.text=basketList[position].count.toString()

        holder.binding.rowRemove.setOnClickListener {
            val product= Product(basketList[position].id,basketList[position].name,basketList[position].price,basketList[position].currency,basketList[position].image)
            viewmodel.deleteProductBasket(product)
        }

        holder.binding.textNegative.setOnClickListener {
            if (holder.binding.rowCountText.text.toString().toInt() > 1) {
                holder.binding.rowCountText.text= (holder.binding.rowCountText.text.toString().toInt() -1).toString()
                val product= Product(basketList[position].id,basketList[position].name,basketList[position].price,basketList[position].currency,basketList[position].image)
                viewmodel.negatifBasket(product)

            } else {
                val product= Product(basketList[position].id,basketList[position].name,basketList[position].price,basketList[position].currency,basketList[position].image)
                viewmodel.deleteProductBasket(product)
            }
        }

        holder.binding.textPositive.setOnClickListener {
            holder.binding.rowCountText.text=(holder.binding.rowCountText.text.toString().toInt() +1).toString()
            val product= Product(basketList[position].id,basketList[position].name,basketList[position].price,basketList[position].currency,basketList[position].image)
            viewmodel.addToBasket(product)
        }


        Glide.with(holder.itemView.context)
            .load(basketList[position].image)
            .into(holder.binding.rowImageView)
    }


    override fun getItemCount(): Int {
        return basketList.size
    }
}