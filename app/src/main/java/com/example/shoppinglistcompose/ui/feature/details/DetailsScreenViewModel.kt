package com.example.shoppinglistcompose.ui.feature.details

import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import com.example.shoppinglistcompose.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    fun getItem(id: Int): Item? {
        //fixme создать метод, в котором из БД будет получаться только айтем с подходящим id
//        val list = shoppingListRepository.getItems()
//        return list.find { it.id == id }
        return shoppingListRepository.getItem(id)
    }
}