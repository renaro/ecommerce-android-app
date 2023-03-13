@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)

package com.renarosantos.ecommerceapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.renarosantos.ecommerceapp.R.*
import com.renarosantos.ecommerceapp.product_list.presentation.ProductCardViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCardItem(
    viewState: ProductCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Card(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        if (LocalInspectionMode.current) { // if Preview
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_slideshow),
                contentDescription = "",
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            GlideImage(
                model = viewState.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = viewState.title,
            style = TextStyle(fontSize = 18.sp),
            maxLines = 2,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)

        )
        Text(
            text = viewState.description,
            style = TextStyle(fontSize = 16.sp),
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            val imageId = if (viewState.isFavorite) {
                drawable.ic_favorite_enabled
            } else {
                drawable.ic_favorite_disabled
            }
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = Color.Red),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable {
                        onFavoriteClick()
                    }
            )
            Text(
                viewState.price,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(4.dp)
            )
        }
        Button(
            onClick = onCartClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC5659)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (viewState.isProductInCart) {
                        drawable.ic_remove_shopping_cart
                    } else {
                        drawable.ic_add_shopping_cart
                    }
                ), contentDescription = "",
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = stringResource(
                    id = if (!viewState.isProductInCart) {
                        string.add_to_cart
                    } else string.remove
                )
            )
        }
    }
}

@Composable
@Preview
fun Preview() {
    ProductCardItem(
        viewState = ProductCardViewState(
            id = "1",
            title = "Playstation 1",
            description = "This is the first playstation console!",
            price = "US $ 200.0",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/androidecommercesample.appspot.com/o/playstation_1.png?alt=media&token=1414f40e-23cf-4f44-b922-e12bfcfca9f3",
            isFavorite = false,
            isProductInCart = false
        ), {}, {}, {}
    )
}

