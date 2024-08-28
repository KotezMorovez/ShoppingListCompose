package com.example.shoppinglistcompose.data.repository

import android.net.Uri
import com.example.shoppinglistcompose.data.service.CacheService
import com.example.shoppinglistcompose.data.service.ShoppingListService
import com.example.shoppinglistcompose.domain.model.Item
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListService: ShoppingListService,
    private val cacheService: CacheService
): ShoppingListRepository {
    override fun addItem(item: Item): List<Item> {
        return shoppingListService.addItem(item)
    }

    override fun deleteItem(id: Int): List<Item> {
        return shoppingListService.deleteItem(id)
    }

    override fun editItem(item: Item): List<Item> {
        return shoppingListService.editItem(item)
    }

    override fun getItems(): List<Item> {
        return shoppingListService.getItems()
    }

    override fun addImageToCache(uri: Uri): Uri? {
        return cacheService.addImageToCache(uri)
    }
}