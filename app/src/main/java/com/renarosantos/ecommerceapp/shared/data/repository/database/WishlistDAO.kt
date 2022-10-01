package com.renarosantos.ecommerceapp.shared.data.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishlistDAO {
    @Insert
    fun insertAll(vararg products: FavoriteProductEntity)

    @Delete
    fun delete(product: FavoriteProductEntity)

    @Query("SELECT * FROM favoriteproductentity WHERE id LIKE :productId")
    fun getFavoriteProducts(productId: String): List<FavoriteProductEntity>
}