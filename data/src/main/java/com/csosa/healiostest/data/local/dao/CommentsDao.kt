package com.csosa.healiostest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csosa.healiostest.data.local.models.CommentEntity

@Dao
interface CommentsDao {

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentsByPostId(postId: Int): List<CommentEntity>

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: CommentEntity?)
}
