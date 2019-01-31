package com.example.signingoogle.roomDatabase.daoInterface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.example.signingoogle.roomDatabase.Score

@Dao
interface QuizScoreInterface {

    @Insert(onConflict = REPLACE)
    fun insert(score: Score)

}