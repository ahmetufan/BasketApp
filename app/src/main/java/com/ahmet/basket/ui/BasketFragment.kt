package com.ahmet.basket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmet.basket.R
import com.ahmet.basket.adapter.BasketAdapter
import com.ahmet.basket.databinding.FragmentBasketBinding
import com.ahmet.basket.databinding.FragmentHomeBinding
import com.ahmet.basket.models.Product
import com.ahmet.basket.utils.Listener
import com.ahmet.basket.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(){

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private var basketAdapter: BasketAdapter? = null



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


            binding.buttonContinue.setOnClickListener {
                val action=BasketFragmentDirections.actionBasketFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            }




        }
    private fun observeLiveData(){

        viewModel.basket.observe(viewLifecycleOwner, Observer { basket ->
            basketAdapter = BasketAdapter(basket,viewModel)
            binding.recyclerBasket.adapter = basketAdapter

            basket.forEach {
                binding.totalCurrency.text=it.currency
            }

        })

        viewModel.totalBasket.observe(viewLifecycleOwner, Observer {
            binding.totalBasket.text = String.format("%.2f",it)

        })


    }



}