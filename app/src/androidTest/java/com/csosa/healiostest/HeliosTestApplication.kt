package com.csosa.healiostest

import android.app.Application
import com.csosa.healiostest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HeliosTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HeliosTestApplication)
            modules(
                fakeNetworkModule,
                appPreferenceModule,
                fakeDataSourceModule,
                useCasesModule,
                viewModelsModule
            )
        }
    }
}
