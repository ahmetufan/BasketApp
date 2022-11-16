package com.ahmet.basket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmet.basket.adapter.HomeAdapter
import com.ahmet.basket.databinding.FragmentHomeBinding
import com.ahmet.basket.models.Product
import com.ahmet.basket.utils.Listener
import com.ahmet.basket.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), Listener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel:HomeViewModel by activityViewModels()
    private lateinit var adaptery:HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()


        initRecycler()

        observeLiveData()


    }

    private fun observeLiveData() {

        viewModel.data.observe(viewLifecycleOwner, Observer { product ->
            product.let {

                adaptery.updateList(product)
            }
        })
    }

    private fun initRecycler() {

        binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),2)
        adaptery=HomeAdapter(arrayListOf(),this)
        binding.recyclerView.adapter=adaptery
    }

    override fun onItemClick(product: Product) {
        //Sepete Ekleme
        viewModel.addToBasket(product)
    }


}