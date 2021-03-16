package com.csosa.healiostest.data.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.healiostest.data.BaseTest
import com.csosa.healiostest.domain.repository.ICommentsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class CommentsRepositoryTest : BaseTest() {

    private lateinit var commentsRepository: ICommentsRepository

    @Before
    override fun setup() {
        super.setup()
        commentsRepository = CommentsRepository(heliosApiService, commentsDao, appPreferences)
    }

    @Test
    fun `when comments are requested at the first time, then return all from api `() {
        runBlocking(Dispatchers.IO) {
            
            val postId = 1 // in comments.json
            val commentsFlow = commentsRepository.getCommentsByPostId(postId)
            val numberOfComments = 5 // in comments.json

            commentsFlow.collect { comments ->
                Truth.assertThat(comments.size).isEqualTo(numberOfComments)
            }
        }
    }

    @Test
    fun `when comments are requested at the second time, then return all from db `() {
        runBlocking(Dispatchers.IO) {

            val postId = 1 // in comments.json
            val commentsFlow = commentsRepository.getCommentsByPostId(postId)
            val numberOfComments = 5 // in comments.json
            var firstCommentId: Int? = null

            //First time
            commentsFlow.collect { comments ->
                firstCommentId = comments.firstOrNull()?.id
            }

            if (firstCommentId != null) {
                //only for testing
                commentsRepository.deleteLocalCommentById(firstCommentId!!).collect()
            }
            //Second time
            commentsFlow.collect { comments ->
                Truth.assertThat(comments.size).isEqualTo(numberOfComments - 1)
            }
        }
    }

    @Test
    fun `when comments are requested for a no existing postId, then return empty list `() {
        runBlocking(Dispatchers.IO) {


            val postId = 1000 // in comments.json
            val commentsFlow = commentsRepository.getCommentsByPostId(postId)
            val numberOfComments = 0// in comments.json

            commentsFlow.collect { comments ->
                Truth.assertThat(comments.size).isEqualTo(numberOfComments)
            }
        }
    }

}
