package com.csosa.healiostest.di

import com.csosa.healiostest.viewmodel.ListPostsViewModel
import com.csosa.healiostest.viewmodel.PostDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        ListPostsViewModel(
            getAllPostsUseCase = get(named("posts"))
        )
    }

    viewModel {
        PostDetailViewModel(
            getCommentsUseCase = get(named("comments")),
            getUserUseCase = get(named("user"))
        )
    }

}
