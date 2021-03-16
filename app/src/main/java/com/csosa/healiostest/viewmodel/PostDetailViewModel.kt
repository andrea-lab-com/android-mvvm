package com.csosa.healiostest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csosa.healiostest.commons.ExceptionHandler
import com.csosa.healiostest.domain.usecases.GetCommentsBaseUseCase
import com.csosa.healiostest.domain.usecases.GetUserBaseUseCase
import com.csosa.healiostest.idlingresource.EspressoIdlingResource
import com.csosa.healiostest.mappers.toPresentation
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.states.Error
import com.csosa.healiostest.models.states.PostDetailsViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

internal class PostDetailViewModel(
    private val getCommentsUseCase: GetCommentsBaseUseCase,
    private val getUserUseCase: GetUserBaseUseCase
) : BaseViewModel() {

    // region Members

    private var postDetailsJob: Job? = null

    val detailViewState: LiveData<PostDetailsViewState>
        get() = _detailViewState

    private var _detailViewState = MutableLiveData<PostDetailsViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _detailViewState.value = _detailViewState.value?.copy(error = Error(message))
    }

    // endregion

    // region Constructor

    init {
        _detailViewState.value =
            PostDetailsViewState(
                isComplete = false,
                error = null,
                user = null,
                info = null,
                comments = null
            )
    }

    // endregion

    // region Android API

    override fun onCleared() {
        super.onCleared()
        postDetailsJob?.cancel()
    }

    // endregion

    // region Public API

    fun getPostDetails(post: PostPresentation, isRetry: Boolean = false) {

        EspressoIdlingResource.increment()
        _detailViewState.value = _detailViewState.value?.copy(info = post)
        if (isRetry) {
            _detailViewState.value = _detailViewState.value?.copy(error = null)
        }
        postDetailsJob = launchCoroutine {
            loadUser(post.userId)
            loadComments(post.id)
            _detailViewState.value = _detailViewState.value?.copy(isComplete = true)
        }
    }

    fun displayPostError(message: Int) {

        EspressoIdlingResource.increment()
        _detailViewState.value = _detailViewState.value?.copy(error = Error(message))
    }

    // endregion

    // region Private API

    private suspend fun loadUser(userId: Int) {

        EspressoIdlingResource.increment()
        getUserUseCase(userId).collect { user ->

            val userPresentation = user?.toPresentation()
            _detailViewState.value = _detailViewState.value?.copy(user = userPresentation)
        }
    }

    private suspend fun loadComments(postId: Int) {

        EspressoIdlingResource.increment()
        getCommentsUseCase(postId).collect { comments ->

            val commentsPresentation = comments.map { comment -> comment.toPresentation() }
            _detailViewState.value = _detailViewState.value?.copy(comments = commentsPresentation)
        }
    }

    // endregion
}
