package com.csosa.healiostest.helpers

import com.csosa.healiostest.sample.comments
import com.csosa.healiostest.sample.posts
import com.csosa.healiostest.sample.users
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class HeliosAppRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            POSTS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(posts)
            }
            USERS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(users)
            }
            COMMENTS_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(comments)
            }
            else -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            }
        }
    }

}
