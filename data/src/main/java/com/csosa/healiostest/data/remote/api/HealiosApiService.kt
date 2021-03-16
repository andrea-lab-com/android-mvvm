package com.csosa.healiostest.data.remote.api

import com.csosa.healiostest.data.remote.models.CommentResponse
import com.csosa.healiostest.data.remote.models.PostResponse
import com.csosa.healiostest.data.remote.models.UserResponse
import retrofit2.http.GET

interface HealiosApiService {

    @GET("posts/")
    suspend fun getAllPosts(): List<PostResponse>

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponse>

    @GET("comments/")
    suspend fun getAllComments(): List<CommentResponse>
}
