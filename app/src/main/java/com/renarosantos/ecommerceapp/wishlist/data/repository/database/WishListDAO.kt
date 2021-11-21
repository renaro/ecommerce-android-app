package com.renarosantos.ecommerceapp.wishlist.data.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.renarosantos.ecommerceapp.product_list.data.ProductEntity

@Dao
interface WishListDAO {

    @Query("SELECT * FROM favoriteproductentity WHERE id=:id")
    fun isProductFavorite(id: String) : FavoriteProductEntity?

    @Insert
    fun addProductToFavorites(product : FavoriteProductEntity)

    @Delete
    fun removeProductFromFavorites(product: FavoriteProductEntity)
}