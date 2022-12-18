package com.renarosantos.ecommerceapp.product_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.renarosantos.ecommerceapp.R
import com.renarosantos.ecommerceapp.databinding.ProductListFragmentBinding
import com.renarosantos.ecommerceapp.product_list.presentation.adapters.ProductCardListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private lateinit var binding: ProductListFragmentBinding
    private lateinit var searchVw: SearchView
    private lateinit var toggleBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ProductListViewModel by viewModels()
    private var data: ArrayList<ProductCardViewState> = arrayListOf()
    private val adapter =
        ProductCardListAdapter(
            ::onItemClicked,
            ::onFavoriteIconClicked,
            ::onBuyItCLicked,
            ::onRemoveClicked
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.product_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews() // Initialize views
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
        if (it.isSuccess) {
            Snackbar.make(binding.coordinator, "Product added to the cart!", Snackbar.LENGTH_SHORT)
                .show()
        } else {
            Snackbar.make(
                binding.coordinator,
                "Product already in the cart!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUI(viewState: ProductListViewState) {
        when (viewState) {
            is ProductListViewState.Content -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                data = (viewState.productList as ArrayList<ProductCardViewState>)
                adapter.setData(data)
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

    override fun onResume() {
        super.onResume()
        adapter.setData(data)
        searchVw.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryText ->
                    val temporalList = data.filter { it.title.contains(queryText, true) }
                    adapter.setData(temporalList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { newTextString ->
                    val temporalList = data.filter { it.title.contains(newTextString, true) }
                    adapter.setData(temporalList)
                }
                return true
            }
        })

        toggleBtn.setOnClickListener {
            setStaggeredGridLayoutManagerOnAdapter()
        }
    }

    // parameter just to show how to retrieve data from Adapter to the fragment
    private fun onItemClicked(viewState: ProductCardViewState) {
        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment())
    }

    private fun onBuyItCLicked(viewState: ProductCardViewState) {
        viewModel.onBuyClicked(viewState.id)
    }

    private fun onRemoveClicked(viewState: ProductCardViewState) {
        viewModel.removeClicked(viewState.id)
    }

    private fun onFavoriteIconClicked(viewState: ProductCardViewState) {
        viewModel.favoriteIconClicked(viewState.id)
    }

    private fun setupProductRecyclerView() {
        recyclerView.adapter = adapter
    }

    private fun initViews() {
        with(binding) {
            searchVw = searchView
            toggleBtn = toggleLayout
            recyclerView = viewProductList
        }
    }

    private fun setStaggeredGridLayoutManagerOnAdapter() {
        val staggeredGridLayoutManager =
            (recyclerView.layoutManager as StaggeredGridLayoutManager).apply {
                spanCount = 2
            }
        recyclerView.layoutManager = staggeredGridLayoutManager
    }

    private fun setLinearLayoutManagerOnAdapter() {
    }
}
