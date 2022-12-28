package com.renarosantos.ecommerceapp.product_list.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.datastore.migrations.SharedPreferencesView
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.renarosantos.ecommerceapp.R
import com.renarosantos.ecommerceapp.databinding.ProductListFragmentBinding
import com.renarosantos.ecommerceapp.product_list.productUtils.ProductUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.AbstractPreferences

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private lateinit var binding: ProductListFragmentBinding
    private val viewModel: ProductListViewModel by viewModels()

    private lateinit var  gridLayoutManager :GridLayoutManager
    private lateinit var sharedPreferences: SharedPreferences
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSharedPreference()
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

        binding.retryBtn.setOnClickListener{
            viewModel.loadProductList()
        }
        binding.switchGridLinearBtn.setOnClickListener{
            switchProductsLayout()
        }
        viewModel.loadProductList()
    }

    override fun onDestroyView() {
        var editor = sharedPreferences.edit()
        editor.putInt(ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_KEY,gridLayoutManager.spanCount)
        editor.commit()
        super.onDestroyView()

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
                //binding.loadingView.isVisible = false
                binding.viewProductList.unVeil()
                adapter.setData(viewState.productList)
                binding.switchGridLinearBtn.isVisible = true
            }
            ProductListViewState.Error -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = true
               // binding.loadingView.isVisible = false
                binding.viewProductList.unVeil()
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                //binding.loadingView.isVisible = true
                binding.viewProductList.veil()
                binding.switchGridLinearBtn.isVisible = false
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
        gridLayoutManager = GridLayoutManager(requireContext(),sharedPreferences
            .getInt(ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_KEY,ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_ONE))

        if (gridLayoutManager.spanCount == ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_TWO){
            switchProductsLayoutIcon(R.drawable.ic_linear)
        }else {
            switchProductsLayoutIcon(R.drawable.ic_grid)
        }
        adapter.changeLayout(gridLayoutManager.spanCount)

        binding.viewProductList.addVeiledItems(ProductUtils.PRODUCTLIST_VEILED_ITEMS)
        binding.viewProductList.setLayoutManager(gridLayoutManager)
        binding.viewProductList.setAdapter(adapter)

    }

    private fun switchProductsLayout() {
        if (gridLayoutManager.spanCount == ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_TWO){
            gridLayoutManager.spanCount = ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_ONE
            switchProductsLayoutIcon(R.drawable.ic_grid)
        }else {
            gridLayoutManager.spanCount = ProductUtils.PRODUCT_GRIDLAYOUT_SPAN_COUNT_TWO
            switchProductsLayoutIcon(R.drawable.ic_linear)
        }
        adapter.changeLayout(gridLayoutManager.spanCount)

    }
    private fun switchProductsLayoutIcon(icon: Int) {
        binding.switchGridLinearBtn
            .setImageDrawable(ContextCompat.getDrawable(requireContext(), icon))
    }

    private fun initSharedPreference() {
        sharedPreferences = requireContext()
            .getSharedPreferences(ProductUtils.sharedPreferencesFileName, Context.MODE_PRIVATE)
    }


}