package com.csosa.healiostest.data.helpers

import com.csosa.healiostest.data.helpers.Constants.COMMENTS_URL
import com.csosa.healiostest.data.helpers.Constants.POSTS_URL
import com.csosa.healiostest.data.helpers.Constants.USERS_URL
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class HeliosRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {

            POSTS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/posts.json"))
            }
            USERS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/users.json"))
            }
            COMMENTS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/comments.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }

}
