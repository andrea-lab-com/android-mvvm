package com.csosa.healiostest.models.states

import com.csosa.healiostest.models.CommentPresentation
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.UserPresentation

internal data class PostDetailsViewState(
    val isComplete: Boolean,
    val error: Error?,
    val user: UserPresentation?,
    val comments: List<CommentPresentation>?,
    val info: PostPresentation?
)
