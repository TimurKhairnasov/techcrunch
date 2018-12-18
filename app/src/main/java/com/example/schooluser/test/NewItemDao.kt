package com.example.schooluser.test

import android.arch.persistence.room.Dao
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface NewItemDao {

    @Query("SELECT * FROM newItems")
    fun getAll(): Flowable<List<NewItem>>

    @Query("SELECT * FROM newItems")
    fun getAllList(): List<NewItem>

    @Query("SELECT * FROM newItems WHERE id = :id")
    fun getById(id: Long): NewItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newItem: NewItem)

    @Update
    fun update(newItem: NewItem)

    @Delete
    fun delete(newItem: NewItem)

}
