package com.csosa.healiostest.data.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class AppPreferences(context: Context) {

    private var sharedPreferences
            = context.getSharedPreferences("HeliosTest.sharedPreferences", MODE_PRIVATE)

    var lastCallPosts: Long?
        get() = getLong(Key.LAST_CALL_POSTS)
        set(value) = setLong(Key.LAST_CALL_POSTS, value)

    var lastCallUsers: Long?
        get() = getLong(Key.LAST_CALL_USERS)
        set(value) = setLong(Key.LAST_CALL_USERS, value)

    var lastCallComments: Long?
        get() = getLong(Key.LAST_CALL_COMMENTS)
        set(value) = setLong(Key.LAST_CALL_COMMENTS, value)


    private fun getLong(key: Key): Long? {

        return if (sharedPreferences.contains(key.name)) {

            sharedPreferences.getLong(key.name, 0)
        } else {
            null
        }
    }

    private fun setLong(key: Key, value: Long?) {

        value?.let {

            sharedPreferences.edit { putLong(key.name, value) }
        } ?: remove(key)
    }

    private fun remove(key: Key) = sharedPreferences.edit { remove(key.name) }

    enum class Key {
        LAST_CALL_POSTS, LAST_CALL_USERS, LAST_CALL_COMMENTS;
    }

}
