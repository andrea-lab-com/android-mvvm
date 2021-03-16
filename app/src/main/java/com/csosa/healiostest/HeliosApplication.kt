package com.csosa.healiostest

import android.app.Application
import com.csosa.healiostest.di.*
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class HeliosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@HeliosApplication)
            modules(
                    networkModule,
                    viewModelsModule,
                    appPreferenceModule,
                    dataSourceModule,
                    useCasesModule
            )
        }

    }

}
