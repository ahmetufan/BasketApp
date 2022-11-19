package com.ahmet.basket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ahmet.basket.R
import com.ahmet.basket.databinding.FragmentBasketBinding
import com.ahmet.basket.databinding.FragmentBasketDetayBinding
import com.ahmet.basket.models.Post
import com.ahmet.basket.models.Product
import com.ahmet.basket.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketDetayFragment : Fragment() {

    private var _binding: FragmentBasketDetayBinding? = null
    private val binding get() = _binding!!

    private val gelenVeri by navArgs<BasketDetayFragmentArgs>()
    private val viewModel: HomeViewModel by activityViewModels()
    private var basketList: List<Product> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gelenVeri.let {

            binding.rowName.text=gelenVeri.basketDetay.name
            binding.rowPrice.text=gelenVeri.basketDetay.price
            binding.rowCurrency.text=gelenVeri.basketDetay.currency


            Glide.with(requireContext())
                .load(gelenVeri.basketDetay.image)
                .into(binding.rowImageView)
        }




    }

}