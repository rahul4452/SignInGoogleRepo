package com.example.signingoogle.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoryTable")
data class Category (
    @PrimaryKey(autoGenerate = true) var category_id: Int?,
    @ColumnInfo(name = "categoryName") var category_name: String?,
    @ColumnInfo(name = "categoryCode") var category_code: Int?
){
    constructor() : this(0,"",null)
}