package com.csosa.healiostest.utils

import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.model.User

object Data {
    val users = mutableListOf(
        User(
        1,
        "Name of user 1",
        "Username of user 1",
        "Email of user 1"),
        User(
            2,
            "Name of user 2",
            "Username of user 2",
            "Email of user 2")
    )

    val comments = mutableListOf(
        Comment(
            1,
            1,
            "Name of comment 1",
            "Email of comment 1",
            "Body of comment 1"
        ),
        Comment(
            1,
            2,
            "Name of comment 2",
            "Email of comment 2",
            "Body of comment 2"
        ),
        Comment(
            1,
            3,
            "Name of comment 3",
            "Email of comment 3",
            "Body of comment 3"
        ),
        Comment(
            2,
            4,
            "Name of comment 4",
            "Email of comment 4",
            "Body of comment 4"
        )
    )
    val posts = mutableListOf(
        Post(
            1,
            1,
            "Title of post 1",
            "Body of post 1"
        ),
        Post(
            1000,
            2,
            "Title of post 2",
            "Body of post 2"
        ),
        Post(
            1,
            3,
            "Title of post 3",
            "Body of post 3"
        )
    )

}
