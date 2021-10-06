package com.example.aposs_buyer.model

data class CartItem(
    val id: Int,
    val image: Image,
    val name: String,
    val price: Int,
    var amount: Int,
    val property: String
)
{
    fun getPriceFormat(): String{
        return "$$price"
    }
}