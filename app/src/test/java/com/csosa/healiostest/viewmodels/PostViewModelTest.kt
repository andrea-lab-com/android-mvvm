package com.csosa.healiostest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.healiostest.BaseViewModelTest
import com.csosa.healiostest.fakes.FakeGetCommentsUseCase
import com.csosa.healiostest.fakes.FakeGetUserUseCase
import com.csosa.healiostest.mappers.toPresentation
import com.csosa.healiostest.utils.Data
import com.csosa.healiostest.utils.UiState
import com.csosa.healiostest.viewmodel.PostDetailViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@ExperimentalCoroutinesApi
internal class PostViewModelTest : BaseViewModelTest() {

    // region Members

    private lateinit var postDetailViewModel: PostDetailViewModel

    // endregion

    // region Tests

    @Test
    fun `given a Post Presentation then get post details`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)
            val postId = 1
            val postPresentation = Data.posts.firstOrNull { it.id == postId }!!.toPresentation()

            postDetailViewModel.getPostDetails(postPresentation)

            postDetailViewModel.detailViewState.observeForever { detailViewState ->

                if (detailViewState.isComplete) {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNotNull()
                    Truth.assertThat(detailViewState.user).isNotNull()
                    Truth.assertThat(detailViewState.comments?.size).isEqualTo(3)
                } else {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNull()
                    Truth.assertThat(detailViewState.user).isNull()
                    Truth.assertThat(detailViewState.comments).isEmpty()
                }
            }
        }
    }

    @Test
    fun `given a Post Presentation with no valid userId then get post details`() {
        coroutineTestRule.dispatcher.runBlockingTest {

            prepareViewModel(UiState.SUCCESS)
            val postId = 2
            val postPresentation = Data.posts.firstOrNull { it.id == postId }!!.toPresentation()

            postDetailViewModel.getPostDetails(postPresentation)

            postDetailViewModel.detailViewState.observeForever { detailViewState ->

                if (detailViewState.isComplete) {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNotNull()
                    Truth.assertThat(detailViewState.user).isNull()
                    Truth.assertThat(detailViewState.comments?.size).isEqualTo(1)
                } else {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNull()
                    Truth.assertThat(detailViewState.user).isNull()
                    Truth.assertThat(detailViewState.comments).isEmpty()
                }
            }
        }
    }

    @Test
    fun `given a Post Presentation without comments then get post details`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)
            val postId = 3
            val postPresentation = Data.posts.firstOrNull { it.id == postId }!!.toPresentation()

            postDetailViewModel.getPostDetails(postPresentation)

            postDetailViewModel.detailViewState.observeForever { detailViewState ->

                if (detailViewState.isComplete) {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNotNull()
                    Truth.assertThat(detailViewState.user).isNotNull()
                    Truth.assertThat(detailViewState.comments).isEmpty()
                } else {

                    Truth.assertThat(detailViewState.error).isNull()
                    Truth.assertThat(detailViewState.info).isNull()
                    Truth.assertThat(detailViewState.user).isNull()
                    Truth.assertThat(detailViewState.comments).isEmpty()
                }
            }
        }
    }

    // endregion

    // region BaseViewModelTest

    override fun prepareViewModel(uiState: UiState) {
        val getUserUseCase = FakeGetUserUseCase(uiState)
        val getCommentsUseCase = FakeGetCommentsUseCase(uiState)

        postDetailViewModel =
            PostDetailViewModel(
                getCommentsUseCase,
                getUserUseCase
            )
    }

    // endregion

    // region Helpers

    @After
    fun clear() {

       // Data.users.clear()
       // Data.comments.clear()
    }

    // endregion
}
