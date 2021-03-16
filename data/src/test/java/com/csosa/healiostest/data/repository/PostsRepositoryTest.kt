package com.csosa.healiostest.data.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.healiostest.data.BaseTest
import com.csosa.healiostest.domain.repository.IPostsRepository
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
internal class PostsRepositoryTest : BaseTest() {

    private lateinit var postsRepository: IPostsRepository

    @Before
    override fun setup() {
        super.setup()
        postsRepository = PostsRepository(heliosApiService, postsDao, appPreferences)
    }

    @Test
    fun `when all posts are requested at the first time, then return all from api `() {
        runBlocking(Dispatchers.IO) {

            val postsFlow = postsRepository.getAllPosts()
            val numberOfPosts = 8 // in post.json

            postsFlow.collect { posts ->
                Truth.assertThat(posts.size).isEqualTo(numberOfPosts)
            }
        }
    }

    @Test
    fun `when all posts are requested at the second time, then return all from db `() {
        runBlocking(Dispatchers.IO) {

            val postsFlow = postsRepository.getAllPosts()
            val numberOfPosts = 8 // in post.json
            var firstPostId: Int? = null

            //First time
            postsFlow.collect { posts ->
                firstPostId = posts.firstOrNull()?.id
            }

            if (firstPostId != null) {
                //only for testing
                postsRepository.deleteLocalPostById(firstPostId!!).collect()
            }
            //Second time
            postsFlow.collect { posts ->
                Truth.assertThat(posts.size).isEqualTo(numberOfPosts - 1)
            }
        }
    }
}
