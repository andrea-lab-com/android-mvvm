package com.csosa.healiostest.domain.usecases

import com.csosa.healiostest.domain.model.Post
import com.csosa.healiostest.domain.repository.IPostsRepository
import kotlinx.coroutines.flow.Flow

typealias GetAllPostsBaseUseCase = BaseUseCase<Unit, Flow<List<Post>>>

class GetAllPostsUseCase(
    private val postsRepository: IPostsRepository
) : GetAllPostsBaseUseCase {

    override suspend fun invoke(params: Unit) = postsRepository.getAllPosts()

}
