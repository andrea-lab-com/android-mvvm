package com.csosa.healiostest.di

import android.content.Context
import com.csosa.healiostest.data.preferences.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appPreferenceModule = module {

    single { provideAppPreference(context = androidContext()) }
}

internal fun provideAppPreference(context: Context): AppPreferences = AppPreferences(context)
