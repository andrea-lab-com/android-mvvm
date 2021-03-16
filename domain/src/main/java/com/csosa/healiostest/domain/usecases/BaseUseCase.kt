package com.csosa.healiostest.domain.usecases


interface BaseUseCase<in T, out R> {
    suspend operator fun invoke(params: T): R
}
