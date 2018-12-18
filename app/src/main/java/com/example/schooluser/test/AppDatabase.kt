package com.example.schooluser.test

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database


@Database(entities = [(NewItem::class), (NewItemRus::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newItemDao(): NewItemDao
    abstract fun newItemRusDao(): NewItemRusDao
}
