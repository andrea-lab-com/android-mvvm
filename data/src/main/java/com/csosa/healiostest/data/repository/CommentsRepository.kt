package com.csosa.healiostest.data.repository

import com.csosa.healiostest.data.local.dao.CommentsDao
import com.csosa.healiostest.data.local.mappers.toDomain
import com.csosa.healiostest.data.local.mappers.toEntity
import com.csosa.healiostest.data.preferences.AppPreferences
import com.csosa.healiostest.data.preferences.IntervalPolicyOfRemoteData
import com.csosa.healiostest.data.preferences.PolicyOfRemoteData
import com.csosa.healiostest.data.remote.api.HealiosApiService
import com.csosa.healiostest.data.remote.mappers.toDomain
import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.repository.ICommentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class CommentsRepository(
    private val apiService: HealiosApiService,
    private val commentsDao: CommentsDao,
    private val appPreferences: AppPreferences
) : ICommentsRepository {


    override suspend fun getCommentsByPostId(postId: Int): Flow<List<Comment>> = flow {

        val mustBeCallToBE = PolicyOfRemoteData.mustBeCallRemote(
                appPreferences.lastCallComments, IntervalPolicyOfRemoteData.EACH_FIVE_MINUTES
        )

        val comments = commentsDao.getCommentsByPostId(postId)

        if (mustBeCallToBE || comments.isEmpty()) {

            commentsDao.deleteAll()
            val allCommentsResponse = apiService.getAllComments()
            for (commentResponse in allCommentsResponse) {

                if (commentResponse.id != null &&
                    commentResponse.postId != null &&
                    commentResponse.name != null&&
                    commentResponse.email != null) {

                    commentsDao.insert(commentResponse.toDomain().toEntity())
                }
            }

            val allCommentsFromDB = commentsDao.getCommentsByPostId(postId)

            appPreferences.lastCallComments = Date().time

            emit(allCommentsFromDB.map { it.toDomain() })
        } else {

            emit(comments.map { it.toDomain() })
        }
    }

    override suspend fun deleteLocalCommentById(id: Int): Flow<Unit> = flow {

        commentsDao.deleteById(id)
        emit(Unit)
    }
}
