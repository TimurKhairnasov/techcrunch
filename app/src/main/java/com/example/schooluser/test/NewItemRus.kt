package com.example.schooluser.test

import android.arch.persistence.room.*


@Entity(tableName = "newItemsRus")
class NewItemRus{
    @PrimaryKey
    var id: Long = 0

    var title: String? = null

    var source: String? = null

    var uri: String? = null

    var url: String? = null


}

