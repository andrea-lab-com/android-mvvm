package com.csosa.healiostest.mappers

import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.model.User
import com.csosa.healiostest.models.*

internal fun Post.toPresentation(): PostPresentation {
    return PostPresentation(this.id, this.title, this.body, this.userId)
}

internal fun User.toPresentation(): UserPresentation {
    return UserPresentation(this.id, this.name, this.username, this.email)
}

internal fun Comment.toPresentation(): CommentPresentation {
    return CommentPresentation(this.id, this.name, this.email, this.body)
}
