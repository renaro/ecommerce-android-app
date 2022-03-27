package com.renarosantos.ecommerceapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

class ProductRepository {

    suspend fun getProductList(): List<ProductCardViewState> {
       /* Only changing this to Dispatchers.IO will fix it.
       The problem is that we were blocking the Main thread,
       which is used by the OS to update the screen(layout updates)
       and therefore the animation was not being shown.
       Since now the "sleep" is being executed in a background thread
       the UI/Main thread can process the layout updates*/
        return withContext(Dispatchers.IO) {
            sleep(2000)
            (1..3).map {
                ProductCardViewState(
                    "Playstation $it",
                    "This is a nice console! Check it out",
                    "200 US$",
                    "https://firebasestorage.googleapis.com/v0/b/androidecommercesample.appspot.com/o/playstation_1.png?alt=media&token=1414f40e-23cf-4f44-b922-e12bfcfca9f3"
                )
            }
        }
    }
}