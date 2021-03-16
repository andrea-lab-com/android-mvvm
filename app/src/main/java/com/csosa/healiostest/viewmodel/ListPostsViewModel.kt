package com.csosa.healiostest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csosa.healiostest.commons.ExceptionHandler
import com.csosa.healiostest.domain.usecases.GetAllPostsBaseUseCase
import com.csosa.healiostest.idlingresource.EspressoIdlingResource
import com.csosa.healiostest.mappers.toPresentation
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.states.Error
import com.csosa.healiostest.models.states.ListPostsViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class ListPostsViewModel(
    private val getAllPostsUseCase: GetAllPostsBaseUseCase
) : BaseViewModel() {

    // region Members

    private var getAllPostJob: Job? = null

    val postsViewState: LiveData<ListPostsViewState>
        get() = _postViewState

    private var _postViewState = MutableLiveData<ListPostsViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _postViewState.value =
            _postViewState.value?.copy(isLoading = false, error = Error(message))
    }

    // endregion

    // region Constructors

    init {
        _postViewState.value =
            ListPostsViewState(isLoading = true, error = null, posts = null)

    }

    // endregion

    // region Android API

    override fun onCleared() {
        super.onCleared()
        getAllPostJob?.cancel()
    }

    // endregion

    // region Public API

    fun executeGetAllPosts() {
        EspressoIdlingResource.increment()

        getAllPostJob?.cancel()
        getAllPostJob = launchCoroutine {
            delay(500)
            onPostsLoading()
            getAllPostsUseCase(Unit).collect { posts ->

                val postsPresentation = posts.map { it.toPresentation() }
                onPostsLoadingComplete(postsPresentation)
            }
        }
    }

    // endregion

    // region Private API

    private fun onPostsLoading() {

        EspressoIdlingResource.increment()
        _postViewState.value = _postViewState.value?.copy(isLoading = true)
    }

    private fun onPostsLoadingComplete(posts: List<PostPresentation>) {

        EspressoIdlingResource.increment()
        _postViewState.value =
            _postViewState.value?.copy(isLoading = false, posts = posts)
    }

    // endregion
}
