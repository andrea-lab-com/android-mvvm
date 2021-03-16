package com.csosa.healiostest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.csosa.healiostest.data.local.dao.CommentsDao
import com.csosa.healiostest.data.local.dao.PostsDao
import com.csosa.healiostest.data.local.dao.UsersDao
import com.csosa.healiostest.data.local.models.CommentEntity
import com.csosa.healiostest.data.local.models.PostEntity
import com.csosa.healiostest.data.local.models.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class, CommentEntity::class], version = 2, exportSchema = false)
abstract class HealiosDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun usersDao(): UsersDao
    abstract fun commentsDao(): CommentsDao
}
