package com.csosa.healiostest.di

import com.csosa.healiostest.domain.repository.ICommentsRepository
import com.csosa.healiostest.domain.repository.IPostsRepository
import com.csosa.healiostest.domain.repository.IUsersRepository
import com.csosa.healiostest.domain.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.module


val useCasesModule = module {

    single(named("posts")) { provideGetAllPostsUseCase(get()) }
    single(named("comments")) { provideGetCommentsUseCase(get()) }
    single(named("user")) { provideGetUserUseCase(get()) }

}

fun provideGetAllPostsUseCase(postsRepository: IPostsRepository): GetAllPostsBaseUseCase {
    return GetAllPostsUseCase(postsRepository)
}

fun provideGetCommentsUseCase(commentsRepository: ICommentsRepository): GetCommentsBaseUseCase {
    return GetCommentsUseCase(commentsRepository)
}

fun provideGetUserUseCase(usersRepository: IUsersRepository): GetUserBaseUseCase {
    return GetUserUseCase(usersRepository)
}
