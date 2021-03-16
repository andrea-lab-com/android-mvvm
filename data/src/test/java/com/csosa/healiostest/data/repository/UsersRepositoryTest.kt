package com.csosa.healiostest.data.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.healiostest.data.BaseTest
import com.csosa.healiostest.domain.repository.IUsersRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class UsersRepositoryTest : BaseTest() {

    private lateinit var usersRepository: IUsersRepository

    @Before
    override fun setup() {
        super.setup()
        usersRepository = UsersRepository(heliosApiService, usersDao, appPreferences)
    }

    @Test
    fun `when user is requested at the first time, then return all from api `() {
        runBlocking(Dispatchers.IO) {

            val userFlow = usersRepository.getUserById(1)
            // from users.json
            val id = 1
            val name = "Leanne Graham"

            userFlow.collect { user ->
                Truth.assertThat(user?.id).isEqualTo(id)
                Truth.assertThat(user?.name).isEqualTo(name)
            }
        }
    }

    @Test
    fun `when user is requested at the second time, then return from db `() {
        runBlocking(Dispatchers.IO) {

            val userFlow = usersRepository.getUserById(1)
            // from users.json
            val id = 1
            val name = "Leanne Graham"
            val newName = "Leanne Graham 1"

            //First time
            userFlow.collect { user ->
                Truth.assertThat(user?.id).isEqualTo(id)
                Truth.assertThat(user?.name).isEqualTo(name)
            }

            //only for testing
            usersRepository.updateLocalName(id, newName).collect()

            //Second time
            userFlow.collect { user ->
                Truth.assertThat(user?.id).isEqualTo(id)
                Truth.assertThat(user?.name).isEqualTo(newName)
            }
        }
    }

    @Test
    fun `when not existing user is requested , then return null `() {
        runBlocking(Dispatchers.IO) {

            val userFlow = usersRepository.getUserById(1000)
            userFlow.collect { user ->
                Truth.assertThat(user).isNull()
            }
        }
    }
}
