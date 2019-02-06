package com.example.signingoogle.roomDatabase.daoInterface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.signingoogle.roomDatabase.Category


@Dao
interface QuizCategoryInterface {

    @Insert(onConflict = REPLACE)
    fun insert(category:Category)

    @Query("SELECT * from categoryTable")
    fun getCategoryData(): List<Category>

    @Query("SELECT COUNT(*) from categoryTable")
    fun usersCount() : Int

    @Query("SELECT * from categoryTable where category_id IS :cID")
    fun getCategoryRow(cID: Int): List<Category>


    @Query("Select * from categoryTable where categoryName IS :cName")
    fun getCategoryCode(cName: String?) : List<Category>

}