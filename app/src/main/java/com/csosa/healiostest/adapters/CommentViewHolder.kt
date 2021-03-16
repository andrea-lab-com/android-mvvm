package com.csosa.healiostest.adapters

import android.view.View
import com.csosa.healiostest.databinding.ItemCommentBinding
import com.csosa.healiostest.models.CommentPresentation
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

internal class CommentViewHolder(
    view: View
) : RecyclerViewHolder<CommentPresentation>(view) {

    val binding = ItemCommentBinding.bind(view)
    override fun bind(position: Int, item: CommentPresentation) {

        super.bind(position, item)
        binding.executePendingBindings()
    }
}
