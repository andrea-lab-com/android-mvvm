package com.csosa.healiostest.domain.repository

import com.csosa.healiostest.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface ICommentsRepository {

    suspend fun getCommentsByPostId(postId: Int): Flow<List<Comment>>
    suspend fun deleteLocalCommentById(id: Int): Flow<Unit>
}
