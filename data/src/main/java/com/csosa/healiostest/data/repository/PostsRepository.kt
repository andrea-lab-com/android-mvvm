package com.csosa.healiostest.data.repository

import com.csosa.healiostest.data.local.dao.PostsDao
import com.csosa.healiostest.data.local.mappers.toDomain
import com.csosa.healiostest.data.local.mappers.toEntity
import com.csosa.healiostest.data.preferences.AppPreferences
import com.csosa.healiostest.data.preferences.IntervalPolicyOfRemoteData
import com.csosa.healiostest.data.preferences.PolicyOfRemoteData
import com.csosa.healiostest.data.remote.api.HealiosApiService
import com.csosa.healiostest.data.remote.mappers.toDomain
import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.repository.IPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class PostsRepository(
    private val apiService: HealiosApiService,
    private val postsDao: PostsDao,
    private val appPreferences: AppPreferences
) : IPostsRepository {


    override suspend fun getAllPosts(): Flow<List<Post>> = flow {

        val mustBeCallToBE = PolicyOfRemoteData.mustBeCallRemote(
                appPreferences.lastCallPosts, IntervalPolicyOfRemoteData.EACH_FIVE_MINUTES
        )
        val count = postsDao.getCount()

        if (mustBeCallToBE || count == 0) {

            postsDao.deleteAll()
            val allPostsResponse = apiService.getAllPosts()
            for (postResponse in allPostsResponse) {

                if (postResponse.id != null &&
                    postResponse.title != null &&
                    postResponse.body != null &&
                    postResponse.userId != null) {

                    postsDao.insert(postResponse.toDomain().toEntity())
                }
            }

            appPreferences.lastCallPosts = Date().time
        }

        val allPostsFromDB = postsDao.getAll()
        emit(allPostsFromDB.map { it.toDomain() })
    }

    override suspend fun deleteLocalPostById(id: Int): Flow<Unit>  = flow {

            postsDao.deleteById(id)
            emit(Unit)
    }

}
