package com.example.schooluser.test

import android.arch.persistence.room.Dao
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface NewItemRusDao {

    @Query("SELECT * FROM newItemsRus")
    fun getAll(): Flowable<List<NewItemRus>>

    @Query("SELECT * FROM newItemsRus")
    fun getAllList(): List<NewItemRus>

    @Query("SELECT * FROM newItems WHERE id = :id")
    fun getById(id: Long): NewItemRus

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newItemRus: NewItemRus)
    @Update
    fun update(newItemRus: NewItemRus)

    @Delete
    fun delete(newItemRus: NewItemRus)

}
