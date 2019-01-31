package com.example.signingoogle.roomDatabase.daoInterface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomWarnings
import com.example.signingoogle.roomDatabase.User

@Dao
interface QuizUserInterface {


    @Query("SELECT * from userTable")
    fun getUserData(): List<User>


    @Query("SELECT * from userTable WHERE userEmail IS :existEmail")
    fun getExistUser(existEmail: String): List<User>


    @Insert(onConflict = REPLACE)
    fun insert(user: User)

//
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("SELECT userTable.*, scoreTable.totalQuestionScore " +
//            "FROM note " +
//            "LEFT JOIN category ON note.category_id = category.id")
//    fun getCategoryNotes(): List<*>




}