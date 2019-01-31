package com.example.signingoogle.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "scoreTable",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("scoreUserId"),
        onDelete = CASCADE
    ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("scoreCategoryId"),
            onDelete = CASCADE)]
)
class Score(
    @PrimaryKey(autoGenerate = true) var score_id: Int,

    @ColumnInfo(name = "userScore") var user_score: Int?,

    @ColumnInfo(name = "scoreUserId") var score_user_id: Int?,

    @ColumnInfo(name = "scoreCategoryId") var score_category_id: Int?,

    @ColumnInfo(name = "totalQuestionScore") var total_question_Score: Int?
) {
    constructor() : this(0,0,0,0,null)
}