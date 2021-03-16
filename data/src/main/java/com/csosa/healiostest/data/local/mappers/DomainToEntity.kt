package com.csosa.healiostest.data.local.mappers

import com.csosa.healiostest.data.local.models.CommentEntity
import com.csosa.healiostest.data.local.models.PostEntity
import com.csosa.healiostest.data.local.models.UserEntity
import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.model.User

internal fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body!!
    )
}

internal fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        username = username,
        email = email
    )
}

internal fun Comment.toEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}

