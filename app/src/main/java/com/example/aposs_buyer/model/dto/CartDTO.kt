package com.example.aposs_buyer.model.dto

data class CartDTO(
    var id: Long,
    var productId: Long,
    var property: String,
    var quantity: Int,
    var imageUrl: String,
    var name: String,
    var price: Int,
    var select: Boolean
)
