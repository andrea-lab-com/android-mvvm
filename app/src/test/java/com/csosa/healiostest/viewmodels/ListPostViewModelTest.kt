package com.csosa.healiostest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.healiostest.BaseViewModelTest
import com.csosa.healiostest.fakes.FakeGetAllPostUseCase
import com.csosa.healiostest.utils.UiState
import com.csosa.healiostest.viewmodel.ListPostsViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE, sdk = [Build.VERSION_CODES.P])
internal class ListPostViewModelTest : BaseViewModelTest() {

    // region Members

    private lateinit var listPostsViewModel: ListPostsViewModel

    // endregion

    // region Tests

    @Test
    fun `should get all post`() {
        coroutineTestRule.dispatcher.runBlockingTest {

            prepareViewModel(UiState.SUCCESS)
            listPostsViewModel.executeGetAllPosts()

            listPostsViewModel.postsViewState.observeForever { state ->

                if (state.isLoading) {

                    Truth.assertThat(state.posts).isNull()
                    Truth.assertThat(state.error).isNull()
                } else {

                    Truth.assertThat(state.error).isNull()
                    Truth.assertThat(state.posts?.size).isEqualTo(3)
                }
            }
        }
    }

    // endregion

    // region BaseViewModelTest

    override fun prepareViewModel(uiState: UiState) {

        val getAllPostUseCase = FakeGetAllPostUseCase(uiState)

        listPostsViewModel = ListPostsViewModel(getAllPostUseCase)

    }

    // endregion

}
