package com.csosa.healiostest.data.remote.mappers

import com.csosa.healiostest.data.remote.models.CommentResponse
import com.csosa.healiostest.data.remote.models.PostResponse
import com.csosa.healiostest.data.remote.models.UserResponse
import com.csosa.healiostest.domain.model.*

internal fun PostResponse.toDomain(): Post {
    return Post(this.userId!!, this.id!!, this.title!!, this.body!!)
}

internal fun UserResponse.toDomain(): User {
    return User(
        this.id!!,
        this.name!!,
        this.username!!,
        this.email!!
    )
}
internal fun CommentResponse.toDomain(): Comment {
    return Comment(this.postId!!, this.id!!, this.name!!, this.email!!, this.body!!)
}

