package com.example.signingoogle.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.signingoogle.roomDatabase.daoInterface.QuizCategoryInterface
import com.example.signingoogle.roomDatabase.daoInterface.QuizScoreInterface
import com.example.signingoogle.roomDatabase.daoInterface.QuizUserInterface


@Database(entities = [User::class, Category::class, Score::class], version = 1,exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizUserInterface(): QuizUserInterface

    abstract fun quizCategoryInterface(): QuizCategoryInterface

    abstract fun quizScoreInterface(): QuizScoreInterface

    companion object {
        private var INSTANCE: QuizDatabase? = null

        fun getInstance(context: Context): QuizDatabase? {
            if (INSTANCE == null) {
                synchronized(QuizDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        QuizDatabase::class.java, "quiz.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}