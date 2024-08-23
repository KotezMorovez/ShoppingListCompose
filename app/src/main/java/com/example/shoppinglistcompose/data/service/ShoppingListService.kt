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
            "$Item.ID = ?",
            arrayOf(item.id.toString())
        )

        return getItems()
    }

    override fun deleteItem(id: Int): List<Item> {
        database.delete(DATABASE_TABLE, "$Item.ID == ?", arrayOf(id.toString()))
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
            val idColumnIndex = cursor.getColumnIndex(Item.ID)
            val nameColumnIndex = cursor.getColumnIndex(Item.NAME)
            val costColumnIndex = cursor.getColumnIndex(Item.COST)
            val categoryColumnIndex = cursor.getColumnIndex(Item.CATEGORY)
            val imageColumnIndex = cursor.getColumnIndex(Item.IMAGE)

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

        contentValues.put(Item.NAME, item.name)
        contentValues.put(Item.COST, item.cost)
        contentValues.put(Item.CATEGORY, item.category)
        contentValues.put(Item.IMAGE, item.image)

        return contentValues
    }

    companion object {
        private const val DATABASE_TABLE = "shopping_list_table"
    }
}