package com.csosa.healiostest.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.csosa.healiostest.data.helpers.HeliosRequestDispatcher
import com.csosa.healiostest.data.local.HealiosDatabase
import com.csosa.healiostest.data.local.dao.CommentsDao
import com.csosa.healiostest.data.local.dao.PostsDao
import com.csosa.healiostest.data.local.dao.UsersDao
import com.csosa.healiostest.data.preferences.AppPreferences
import com.csosa.healiostest.data.remote.api.HealiosApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

internal open class BaseTest {

    private lateinit var mockWebServer: MockWebServer

    lateinit var heliosApiService: HealiosApiService

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var loggingInterceptor: HttpLoggingInterceptor

    private lateinit var db: HealiosDatabase

    protected lateinit var postsDao: PostsDao

    protected lateinit var usersDao: UsersDao

    protected lateinit var commentsDao: CommentsDao

    protected lateinit var appPreferences: AppPreferences


    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = HeliosRequestDispatcher()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkHttpClient(loggingInterceptor)

        val baseUrl = mockWebServer.url("/")
        heliosApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HealiosApiService::class.java)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, HealiosDatabase::class.java).build()
        usersDao = db.usersDao()
        postsDao = db.postsDao()
        commentsDao = db.commentsDao()

        appPreferences = AppPreferences(context)
    }

    @After
    @Throws(IOException::class)
    open fun tearDown() {
        mockWebServer.shutdown()
        runBlocking(Dispatchers.IO) {
            db.clearAllTables()
        }

        db.close()
    }

    private fun buildOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

}
