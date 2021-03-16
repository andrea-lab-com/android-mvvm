package com.csosa.healiostest.activities

import com.csosa.healiostest.models.CommentPresentation
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.UserPresentation

internal interface IPostDetailBinder {

    fun bindPostDetail(post: PostPresentation?)

    fun bindUser(user: UserPresentation?)

    fun bindComments(comments: List<CommentPresentation>?)
}
