package com.csosa.healiostest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csosa.healiostest.data.local.models.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("UPDATE users SET name = :newName WHERE id = :id")
    suspend fun updateName(id: Int, newName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: UserEntity?)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
