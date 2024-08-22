package com.example.shoppinglistcompose.data.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject

class DatabaseHelper @Inject constructor(
    context: Context
) : SQLiteOpenHelper(
    context,
    SHOPPING_LIST_DB,
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table if not exists $DATABASE_TABLE ("
                    + "id integer primary key autoincrement,"
                    + "name text not null,"
                    + "category text not null,"
                    + "cost text not null,"
                    + "image text not null default \"\""
                    + ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val SHOPPING_LIST_DB = "shopping_list_db"
        private const val DATABASE_TABLE = "shopping_list_table"
    }
}