package com.csosa.healiostest.domain.usecases

import com.csosa.healiostest.domain.model.Comment
import com.csosa.healiostest.domain.repository.ICommentsRepository
import kotlinx.coroutines.flow.Flow

typealias GetCommentsBaseUseCase = BaseUseCase<Int, Flow<List<Comment>>>

class GetCommentsUseCase(
    private val commentsRepository: ICommentsRepository
) : GetCommentsBaseUseCase {

    override suspend fun invoke(params: Int)
            = commentsRepository.getCommentsByPostId(params)
}
