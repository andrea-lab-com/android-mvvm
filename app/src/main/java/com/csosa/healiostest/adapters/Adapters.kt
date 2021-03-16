package com.csosa.healiostest.adapters

import com.csosa.healiostest.R
import com.csosa.healiostest.models.CommentPresentation
import com.csosa.healiostest.models.PostPresentation
import me.ibrahimyilmaz.kiel.adapterOf

@Suppress("NOTHING_TO_INLINE")
internal inline fun createPostsAdapter(noinline onClick: (PostPresentation) -> Unit) =
    adapterOf<PostPresentation> {
        diff(
            areItemsTheSame = { old, new -> old === new },
            areContentsTheSame = { old, new ->
                old.id == new.id
            }
        )
        register(
            layoutResource = R.layout.item_post,
            viewHolder = ::PostViewHolder,
            onBindViewHolder = { vh, _, postPresentation ->
                vh.binding.post = postPresentation
                vh.binding.postShowDetailButton.setOnClickListener {
                    onClick(postPresentation)
                }
            }
        )
    }

internal fun createCommentsAdapter() =
    adapterOf<CommentPresentation> {
        diff(
            areItemsTheSame = { old, new -> old === new },
            areContentsTheSame = { old, new ->
                old.id == new.id
            }
        )
        register(
            layoutResource = R.layout.item_comment,
            viewHolder = ::CommentViewHolder,
            onBindViewHolder = { vh, _, commentPresentation ->
                vh.binding.comment = commentPresentation
            }
        )
    }
