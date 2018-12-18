package com.example.schooluser.test

import android.arch.persistence.room.Room

object DB {
    val instance by lazy {
        Room.databaseBuilder(App.instance, AppDatabase::class.java, "database").build()
    }
}
