package com.csosa.healiostest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csosa.healiostest.data.local.models.PostEntity


@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity?)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Query("DELETE FROM posts WHERE id=:id")
    suspend fun deleteById(id: Int)
}
