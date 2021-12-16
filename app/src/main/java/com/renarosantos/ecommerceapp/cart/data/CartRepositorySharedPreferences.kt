package com.renarosantos.ecommerceapp.cart.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class CartRepositorySharedPreferences(private val context: Context) : CartRepository {

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun observeChanges(): Flow<List<String>> {
        return context.dataStore.data
            .catch {
                emptyList<String>()
            }
            .map { preferences ->
                preferences[KEY_CART_SET]?.toList() ?: emptyList()
            }
    }

    override suspend fun addToCart(productId: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[KEY_CART_SET] ?: emptySet()
            val newSet = mutableSetOf<String>()
            newSet.addAll(currentSet)
            newSet.add(productId)
            preferences[KEY_CART_SET] = newSet
        }
    }

    override suspend fun removeFromCart(id: String) {
        context.dataStore.edit { preferences ->
            val currentSet = preferences[KEY_CART_SET] ?: emptySet()
            val newSet = mutableSetOf<String>()
            newSet.addAll(currentSet)
            newSet.remove(id)
            preferences[KEY_CART_SET] = newSet
        }
    }

    companion object {
        val KEY_CART_SET = stringSetPreferencesKey("KEY_CART_SET")
        private const val USER_PREFERENCES_NAME = "com.renarosantos.ecommerceapp.cart"
    }
}