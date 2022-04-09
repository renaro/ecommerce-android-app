package com.renarosantos.ecommerceapp.product_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.renarosantos.ecommerceapp.databinding.ProductListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private lateinit var binding: ProductListFragmentBinding
    private val viewModel: ProductListViewModel by viewModels()
    private val adapter =
        ProductCardListAdapter(::onItemClicked, ::onFavoriteIconClicked, ::onBuyItCLicked, ::onRemoveClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUI(viewState)
        }
        viewModel.cartEvents.observeEvent(viewLifecycleOwner) {
            it?.let {
                updateUiForEvent(it)
            }
        }
        viewModel.loadProductList()
    }

    private fun updateUiForEvent(it: ProductListViewModel.AddToCartEvent) {
        if(it.isSuccess){
            Snackbar.make(binding.coordinator, "Product added to the cart!", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.coordinator, "Product already in the cart!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(viewState: ProductListViewState) {
        when (viewState) {
            is ProductListViewState.Content -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.productList)
            }
            ProductListViewState.Error -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }

    // parameter just to show how to retrieve data from Adapter to the fragment
    private fun onItemClicked(viewState: ProductCardViewState) {
        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment())
    }

    private fun onBuyItCLicked(viewState: ProductCardViewState) {
        viewModel.onBuyClicked(viewState.id)
    }
    private fun onRemoveClicked(viewState: ProductCardViewState){
        viewModel.removeClicked(viewState.id)
    }

    private fun onFavoriteIconClicked(viewState: ProductCardViewState) {
        viewModel.favoriteIconClicked(viewState.id)
    }

    private fun setupProductRecyclerView() {
        binding.viewProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.viewProductList.adapter = adapter
    }
}