package com.renarosantos.ecommerceapp.shared.presentation

interface PriceFormatter {
    fun format(value : Double) : String
}

object InternationalPriceFormatter : PriceFormatter {

    override fun format(value: Double): String {
        return "US $${value}"
    }

}