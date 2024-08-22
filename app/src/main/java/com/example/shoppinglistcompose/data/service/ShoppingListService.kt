package com.example.shoppinglistcompose.data.service

import android.content.ContentValues
import com.example.shoppinglistcompose.domain.model.Item
import javax.inject.Inject

interface ShoppingListService {
    fun addItem(item: Item): List<Item>
    fun editItem(item: Item): List<Item>
    fun deleteItem(id: Int): List<Item>
    fun getItems(): List<Item>
}

class ShoppingListServiceImpl @Inject constructor(
    dbHelper: DatabaseHelper
) : ShoppingListService {
    private val database = dbHelper.writableDatabase

    override fun addItem(item: Item): List<Item> {
        val contentValues = createAndFillContentValues(item)

        database.insert(DATABASE_TABLE, null, contentValues)
        return getItems()
    }

    override fun editItem(item: Item): List<Item> {
        val contentValues = createAndFillContentValues(item)

        database.update(
            DATABASE_TABLE,
            contentValues,
            "$ITEM_ID = ?",
            arrayOf(item.id.toString())
        )

        return getItems()
    }

    override fun deleteItem(id: Int): List<Item> {
        database.delete(DATABASE_TABLE, "$ITEM_ID == ?", arrayOf(id.toString()))
        return getItems()
    }


    override fun getItems(): List<Item> {
        val list = mutableListOf<Item>()
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex(ITEM_ID)
            val nameColumnIndex = cursor.getColumnIndex(ITEM_NAME)
            val costColumnIndex = cursor.getColumnIndex(ITEM_COST)
            val categoryColumnIndex = cursor.getColumnIndex(ITEM_CATEGORY)
            val imageColumnIndex = cursor.getColumnIndex(ITEM_IMAGE)

            do {
                list.add(
                    Item(
                        id = cursor.getInt(idColumnIndex),
                        name = cursor.getString(nameColumnIndex),
                        cost = cursor.getString(costColumnIndex),
                        category = cursor.getString(categoryColumnIndex),
                        image = cursor.getString(imageColumnIndex)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    private fun createAndFillContentValues(item: Item): ContentValues {
        val contentValues = ContentValues()

        contentValues.put(ITEM_NAME, item.name)
        contentValues.put(ITEM_COST, item.cost)
        contentValues.put(ITEM_CATEGORY, item.category)
        contentValues.put(ITEM_IMAGE, item.image)

        return contentValues
    }

    companion object {
        private const val DATABASE_TABLE = "shopping_list_table"
        private const val ITEM_ID = "id"
        private const val ITEM_NAME = "name"
        private const val ITEM_COST = "cost"
        private const val ITEM_CATEGORY = "category"
        private const val ITEM_IMAGE = "image"
    }
}