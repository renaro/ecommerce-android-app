package com.renarosantos.ecommerceapp.wishlist.data.repository.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.renarosantos.ecommerceapp.wishlist.data.repository.WishlistRepository

class WishlistSharedPrefRepo(context: Context) : WishlistRepository {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_FILE_WISHLIST, Context.MODE_PRIVATE
    )

    override suspend fun isFavorite(productId: String): Boolean {
        with(sharedPref) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            return favorites!!.contains(productId)
        }
    }

    override suspend fun addToWishlist(productId: String) {
        with(sharedPref) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            mutableSetOf<String>().run {
                this.addAll(favorites!!.toList())
                this.add(productId)
                sharedPref.edit().putStringSet(KEY_FAVORITES_SET, this).apply()
            }
        }
    }

    override suspend fun removeFromWishlist(productId: String) {
        with(sharedPref) {
            val favorites = getStringSet(KEY_FAVORITES_SET, emptySet())
            mutableSetOf<String>().run {
                this.addAll(favorites!!.toList())
                this.remove(productId)
                sharedPref.edit().putStringSet(KEY_FAVORITES_SET, this).apply()
            }
        }
    }

    companion object {
        const val SHARED_PREFS_FILE_WISHLIST = "com.renarosantos.ecommerceapp.wishlist"
        const val KEY_FAVORITES_SET = "KEY_FAVORITES_SET"
    }
}