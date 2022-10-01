package com.renarosantos.ecommerceapp.shared.data.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProductEntity(
    @PrimaryKey val id : String,
    @ColumnInfo val title : String
)
