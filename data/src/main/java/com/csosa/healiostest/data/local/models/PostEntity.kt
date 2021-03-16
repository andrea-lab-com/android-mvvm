package com.csosa.healiostest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String
)
