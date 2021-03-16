package com.csosa.healiostest.adapters

import android.view.View
import com.csosa.healiostest.databinding.ItemPostBinding
import com.csosa.healiostest.models.PostPresentation
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder


internal class PostViewHolder(
    view: View
) : RecyclerViewHolder<PostPresentation>(view) {

    val binding = ItemPostBinding.bind(view)
    override fun bind(position: Int, item: PostPresentation) {

        super.bind(position, item)
        binding.executePendingBindings()
    }
}
