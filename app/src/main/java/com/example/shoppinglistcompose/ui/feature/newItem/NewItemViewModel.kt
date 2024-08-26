package com.example.shoppinglistcompose.ui.feature.newItem

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import com.example.shoppinglistcompose.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewItemViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    fun addItem(item: Item) {
        shoppingListRepository.addItem(item)
    }

    fun addToCache(uri: Uri): Uri? {
        return shoppingListRepository.addImageToCache(uri)
    }
}