package com.csosa.healiostest.di

import androidx.room.Room
import com.csosa.healiostest.data.local.HealiosDatabase
import com.csosa.healiostest.data.local.dao.CommentsDao
import com.csosa.healiostest.data.local.dao.PostsDao
import com.csosa.healiostest.data.local.dao.UsersDao
import com.csosa.healiostest.data.repository.CommentsRepository
import com.csosa.healiostest.data.repository.PostsRepository
import com.csosa.healiostest.data.repository.UsersRepository
import com.csosa.healiostest.domain.repository.ICommentsRepository
import com.csosa.healiostest.domain.repository.IPostsRepository
import com.csosa.healiostest.domain.repository.IUsersRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {

    single<IPostsRepository> { PostsRepository(postsDao = get(), apiService = get(), appPreferences = get()) }
    single<IUsersRepository> { UsersRepository(usersDao = get(), apiService = get(), appPreferences = get()) }
    single<ICommentsRepository> { CommentsRepository(commentsDao = get(), apiService = get(), appPreferences = get()) }

    single {
        Room
            .databaseBuilder(androidContext(), HealiosDatabase::class.java, "helios_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { providePostsDao(db = get()) }
    single { provideUsersDao(db = get()) }
    single { provideCommentsDao(db = get()) }
}

internal fun providePostsDao(db: HealiosDatabase): PostsDao = db.postsDao()
internal fun provideUsersDao(db: HealiosDatabase): UsersDao = db.usersDao()
internal fun provideCommentsDao(db: HealiosDatabase): CommentsDao = db.commentsDao()
