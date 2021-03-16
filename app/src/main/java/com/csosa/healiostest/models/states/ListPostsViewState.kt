package com.csosa.healiostest.models.states

import com.csosa.healiostest.models.PostPresentation

internal data class ListPostsViewState(
    val isLoading: Boolean,
    val error: Error?,
    val posts: List<PostPresentation>?
)
