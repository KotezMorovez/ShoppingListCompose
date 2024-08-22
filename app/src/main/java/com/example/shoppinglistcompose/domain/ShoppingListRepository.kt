package com.example.shoppinglistcompose.domain

import com.example.shoppinglistcompose.domain.model.Item

interface ShoppingListRepository {
    fun addItem(item: Item): List<Item>
    fun deleteItem(id: Int): List<Item>
    fun editItem(item: Item): List<Item>
    fun getItems(): List<Item>
}