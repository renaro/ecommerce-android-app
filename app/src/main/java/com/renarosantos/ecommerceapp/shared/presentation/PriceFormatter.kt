package com.renarosantos.ecommerceapp.shared.presentation

import java.text.DecimalFormat

interface PriceFormatter {
    fun format(value: Double): String
}

object InternationalPriceFormatter : PriceFormatter {

    override fun format(value: Double): String {
        val dollarSymbol = "\u0024"
        val format = DecimalFormat("#,###.00")
        return "$dollarSymbol ${format.format(value)}"
    }
}
