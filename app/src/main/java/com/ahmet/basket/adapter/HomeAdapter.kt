package com.ahmet.basket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.basket.databinding.RowItemBinding
import com.ahmet.basket.models.Product
import com.ahmet.basket.ui.HomeFragmentDirections
import com.ahmet.basket.utils.Listener
import com.bumptech.glide.Glide

class HomeAdapter(private val list:ArrayList<Product>, private val listener:Listener): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(val binding:RowItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.nameRow.text=list[position].name
        holder.binding.priceRow.text=list[position].price
        holder.binding.currencyRow.text=list[position].currency



        Glide.with(holder.itemView.context)
            .load(list[position].image)
            .into(holder.binding.imageRow)

        holder.binding.addButton.setOnClickListener {
            Toast.makeText(it.context, "Sepete Eklendi ${list[position].name}", Toast.LENGTH_SHORT).show()
            listener.onItemClick(list[position])

            val action=HomeFragmentDirections.actionHomeFragmentToBasketFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<Product>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}