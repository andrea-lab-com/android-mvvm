package com.csosa.healiostest

import androidx.test.espresso.IdlingRegistry
import com.csosa.healiostest.helpers.HeliosAppRequestDispatcher
import com.csosa.healiostest.idlingresource.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

open class BaseTest : KoinTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = HeliosAppRequestDispatcher()
        mockWebServer.start(8082)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }


    @After
    open fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}
