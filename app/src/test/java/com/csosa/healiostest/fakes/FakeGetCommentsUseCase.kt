package com.csosa.healiostest.fakes

import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.usecases.GetCommentsBaseUseCase
import com.csosa.healiostest.utils.Data
import com.csosa.healiostest.utils.UiState
import kotlinx.coroutines.flow.Flow

class FakeGetCommentsUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Comment>, Int>(uiState), GetCommentsBaseUseCase {

    override suspend fun invoke(params: Int): Flow<List<Comment>> = execute(params)

    override fun getValue(params: Int): List<Comment> {

        return Data.comments.filter { it.postId == params }
    }
}
