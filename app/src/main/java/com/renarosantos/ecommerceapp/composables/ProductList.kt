package com.renarosantos.ecommerceapp.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renarosantos.ecommerceapp.product_list.presentation.ProductCardViewState

@Composable
fun ProductList(
    cards: List<ProductCardViewState>,
    onClick: (viewState: ProductCardViewState) -> Unit,
    onFavoriteClick: (viewState: ProductCardViewState) -> Unit,
    onCartClick: (viewState: ProductCardViewState) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp)
    ) {
        items(cards) {
            ProductCardItem(
                viewState = it,
                onClick = { onClick(it) },
                onFavoriteClick = { onFavoriteClick(it) },
                onCartClick = { onCartClick(it) },
            )
        }
    }
}


@Preview
@Composable
fun List() {
    ProductList((1..6).map {
        ProductCardViewState(
            it.toString(),
            "Playstation $it",
            "This is a nice console! Check it out",
            "200 US$",
            "https://firebasestorage.googleapis.com/v0/b/androidecommercesample.appspot.com/o/playstation_1.png?alt=media&token=1414f40e-23cf-4f44-b922-e12bfcfca9f3",
            false,
            false
        )
    },{},{},{})
}