package com.csosa.healiostest.domain.repository

import com.csosa.healiostest.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUsersRepository {

    suspend fun getUserById(id: Int): Flow<User?>
    suspend fun updateLocalName(id: Int, newName: String): Flow<Unit>
}
