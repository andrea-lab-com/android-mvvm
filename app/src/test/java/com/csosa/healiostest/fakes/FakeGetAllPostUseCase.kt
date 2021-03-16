package com.csosa.healiostest.fakes

import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.usecases.GetAllPostsBaseUseCase
import com.csosa.healiostest.utils.Data
import com.csosa.healiostest.utils.UiState
import kotlinx.coroutines.flow.Flow

class FakeGetAllPostUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Post>, Unit>(uiState), GetAllPostsBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<List<Post>> = execute(params)

    override fun getValue(params: Unit): List<Post> = Data.posts
}
