package com.ahmet.basket.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmet.basket.adapter.BasketAdapter
import com.ahmet.basket.databinding.FragmentBasketBinding
import com.ahmet.basket.models.Post
import com.ahmet.basket.models.Product
import com.ahmet.basket.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject


@AndroidEntryPoint
class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private var basketAdapter: BasketAdapter? = null
    private var products: List<Product> = listOf()
    private var amountt: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerBasket.layoutManager = LinearLayoutManager(requireContext())

        observeLiveData()


        placeOrder()

        binding.buttonContinue.setOnClickListener {
            val action = BasketFragmentDirections.actionBasketFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    private fun placeOrder() {

        binding.buttonPlace.setOnClickListener {

            val newList = mutableListOf<Post>()
            products.forEach { product ->
                newList.add(Post(id = product.id, amount = amountt.toInt()))
            }

            if (newList.isNotEmpty()) {

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.pushPost(newList)

                    for (i in products) {
                        viewModel.deleteProductBasket(i)
                    }
                }
            }

        }
    }

    private fun observeLiveData() {

        viewModel.basket.observe(viewLifecycleOwner, Observer { basket ->
            products = basket
            basketAdapter = BasketAdapter(basket, viewModel)
            binding.recyclerBasket.adapter = basketAdapter

            basket.forEach {
                binding.totalCurrency.text = it.currency
            }
        })

        viewModel.totalBasket.observe(viewLifecycleOwner, Observer {
            amountt = it
            binding.totalBasket.text = String.format("%.2f", it)
        })


        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->

            if (response != null) {

                if (response.isSuccessful) {

                    Toast.makeText(context, "Siparişiniz alındı", Toast.LENGTH_SHORT).show()
                    Log.e("Tagstatus", response.body()?.status ?: "gelmedi")

                } else {

                    val jsonObj = JSONObject(
                        response.errorBody()!!.source().buffer?.snapshot()?.utf8().toString()
                    )
                    Toast.makeText(context, jsonObj.toString(), Toast.LENGTH_SHORT).show()

                    Log.e("TAGerror", jsonObj.toString())

                }
                viewModel.myResponse.value = null
            }
        })
    }

}