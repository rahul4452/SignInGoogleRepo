package com.example.signingoogle.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "userTable")
class User(
    @PrimaryKey(autoGenerate = true) var user_id: Int?,
    @ColumnInfo(name = "userEmail") var user_email: String?,
    @ColumnInfo(name = "userName") var user_name: String?,
    @ColumnInfo(name = "profile_image_url") var user_profile_image: String

)