package com.csosa.healiostest.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.csosa.healiostest.HeliosTestApplication


class MockTestRunner:AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HeliosTestApplication::class.java.name, context)
    }
}
