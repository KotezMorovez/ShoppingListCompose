package com.example.shoppinglistcompose.domain.model

data class Item(
    val id: Int,
    val image: String,
    val name: String,
    val category: String,
    val cost: String
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val COST = "cost"
        const val CATEGORY = "category"
        const val IMAGE = "image"
    }
}
