package com.renarosantos.ecommerceapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.renarosantos.ecommerceapp.presentation.ProductDetailsViewModel
import com.renarosantos.ecommerceapp.presentation.viewstate.ProductDetailsViewState
import com.renarosantos.ecommerceapp.databinding.ProductDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: ProductDetailsFragmentBinding
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProduct("someProductId")
        viewModel.viewState.observe(viewLifecycleOwner){
           updateUi(it)
        }
    }

    private fun updateUi(viewState: ProductDetailsViewState) {
        when(viewState){
            is ProductDetailsViewState.Content -> {
                with(binding){
                    binding.loadingView.isVisible = false
                    val product = viewState.product
                    viewProductTitle.text = product.title
                    Glide.with(requireContext()).
                    load(product.imageUrl)
                        .into(viewProductImage)
                    binding.viewPrice.text = product.price
                    binding.viewFullDescription.text = product.fullDescription
                }
            }
            ProductDetailsViewState.Error -> {
                binding.loadingView.isVisible = false
            }
            ProductDetailsViewState.Loading -> {
                binding.loadingView.isVisible = true
            }
        }
    }
}