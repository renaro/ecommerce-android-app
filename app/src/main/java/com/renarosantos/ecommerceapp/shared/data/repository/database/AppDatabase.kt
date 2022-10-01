package com.renarosantos.ecommerceapp.shared.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWishlistDAO(): WishlistDAO
}