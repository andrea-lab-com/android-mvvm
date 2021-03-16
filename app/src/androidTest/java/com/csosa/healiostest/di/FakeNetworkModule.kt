package com.csosa.healiostest.di

import org.koin.dsl.module

val fakeNetworkModule = module {

    single { provideRetrofit(okHttpClient = get(), url = "http://localhost:8082/") }

    single { provideHealiosApiService(retrofit = get()) }

    single { provideOkHttpClient() }
}
