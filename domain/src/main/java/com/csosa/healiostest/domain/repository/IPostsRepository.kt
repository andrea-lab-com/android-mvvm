package com.csosa.healiostest.domain.repository

import com.csosa.healiostest.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface IPostsRepository {

    suspend fun getAllPosts(): Flow<List<Post>>
    suspend fun deleteLocalPostById(id: Int): Flow<Unit>
}
