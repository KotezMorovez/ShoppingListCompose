package com.example.shoppinglistcompose.ui.feature.shoppinglist

import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.domain.model.Item
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    private val _itemsList = MutableStateFlow<List<Item>>(listOf())
    val itemsList = _itemsList.asStateFlow()

    fun deleteItem(id: Int) {
        _itemsList.value = shoppingListRepository.deleteItem(id)
    }

    fun editItem(item: Item) {
        _itemsList.value = shoppingListRepository.editItem(item)
    }

    fun getItems() {
        _itemsList.value = shoppingListRepository.getItems()
    }
}