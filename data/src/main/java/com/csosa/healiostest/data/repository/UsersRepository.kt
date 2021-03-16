package com.csosa.healiostest.data.repository

import com.csosa.healiostest.data.local.dao.UsersDao
import com.csosa.healiostest.data.local.mappers.toDomain
import com.csosa.healiostest.data.local.mappers.toEntity
import com.csosa.healiostest.data.preferences.AppPreferences
import com.csosa.healiostest.data.preferences.IntervalPolicyOfRemoteData
import com.csosa.healiostest.data.preferences.PolicyOfRemoteData
import com.csosa.healiostest.data.remote.api.HealiosApiService
import com.csosa.healiostest.data.remote.mappers.toDomain
import com.csosa.healiostest.domain.model.User
import com.csosa.healiostest.domain.repository.IUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class UsersRepository(
    private val apiService: HealiosApiService,
    private val usersDao: UsersDao,
    private val appPreferences: AppPreferences
) : IUsersRepository {


    override suspend fun getUserById(id: Int): Flow<User?> = flow {

        val mustBeCallToBE = PolicyOfRemoteData.mustBeCallRemote(
                appPreferences.lastCallUsers, IntervalPolicyOfRemoteData.EACH_FIVE_MINUTES
        )

        val user = usersDao.getUserById(id)

        if (mustBeCallToBE || user == null) {

            usersDao.deleteAll()

            val allUsersResponse = apiService.getAllUsers()
            for (userResponse in allUsersResponse) {

                if (userResponse.id != null &&
                    userResponse.name != null &&
                    userResponse.email != null &&
                    userResponse.username != null) {

                    usersDao.insert(userResponse.toDomain().toEntity())
                }
            }

            appPreferences.lastCallUsers = Date().time
            val userFromDB = usersDao.getUserById(id)
            emit(userFromDB?.toDomain())
        } else {

            emit(user.toDomain())
        }
    }


    override suspend fun updateLocalName(id: Int, newName: String): Flow<Unit> = flow {

        usersDao.updateName(id, newName)
        emit(Unit)
    }
}
