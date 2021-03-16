package com.csosa.healiostest.fakes

import com.csosa.healiostest.domain.model.User
import com.csosa.healiostest.domain.usecases.GetUserBaseUseCase
import com.csosa.healiostest.utils.Data
import com.csosa.healiostest.utils.UiState

class FakeGetUserUseCase(
    uiState: UiState
) : BaseTestUseCase<User?, Int>(uiState), GetUserBaseUseCase {

    override suspend fun invoke(params: Int) = execute(params)

    override fun getValue(params: Int): User? {

        return Data.users.firstOrNull { it.id == params }
    }

}
