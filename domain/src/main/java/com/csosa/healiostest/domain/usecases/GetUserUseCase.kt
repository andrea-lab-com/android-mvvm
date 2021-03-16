package com.csosa.healiostest.domain.usecases

import com.csosa.healiostest.domain.model.User
import com.csosa.healiostest.domain.repository.IUsersRepository
import kotlinx.coroutines.flow.Flow

typealias GetUserBaseUseCase = BaseUseCase<Int, Flow<User?>>

class GetUserUseCase(
    private val userRepository: IUsersRepository
) : GetUserBaseUseCase {

    override suspend fun invoke(params: Int)
            = userRepository.getUserById(params)

}
