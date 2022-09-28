package com.renarosantos.ecommerceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.renarosantos.ecommerceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val adapter = ProductCardListAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewProductList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewProductList.adapter = adapter
        updateUI(ProductListViewState.Content(
            (1..3).map {
                ProductCardViewState(
                    "Product $it",
                    "Description of product",
                    "100 US$", ""
                )
            }
        ))
    }

    private fun updateUI(viewState : ProductListViewState) {
        when(viewState){
            ProductListViewState.Error -> {
                binding.viewProductList.isVisible = false
                binding.loadingView.isVisible = false
                binding.errorView.isVisible = true
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = false
                binding.loadingView.isVisible = true
                binding.errorView.isVisible = false
            }
            is ProductListViewState.Content -> {
                adapter.setData(viewState.productList)
                binding.loadingView.isVisible = false
                binding.errorView.isVisible = false
            }
        }
    }
}