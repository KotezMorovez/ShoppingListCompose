package com.example.shoppinglistcompose.ui.feature.shoppinglist

import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.domain.model.Item
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import com.example.shoppinglistcompose.domain.model.ItemListMock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    private val _itemsList = MutableStateFlow<List<Item>>(ItemListMock.list)
    val itemsList = _itemsList.asStateFlow()

    fun addItem(item: Item) {
        _itemsList.value = shoppingListRepository.addItem(item)
    }

    fun deleteItem(id: Int) {
//        itemsList = shoppinglistRepository.deleteItem(id)
        _itemsList.value = itemsList.value.filter { it.id != id }
    }

    fun editItem(item: Item) {
        _itemsList.value = shoppingListRepository.editItem(item)
    }

    fun getItems() {
        _itemsList.value = shoppingListRepository.getItems()
    }
}