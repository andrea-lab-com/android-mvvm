package com.csosa.healiostest.data.preferences

import java.util.*

object PolicyOfRemoteData {

    fun mustBeCallRemote(lastCallTime: Long?, interval: IntervalPolicyOfRemoteData): Boolean {

        return if (lastCallTime != null) {

            val currentTime = Date().time
            (currentTime - lastCallTime) > interval.value
        } else {

            true
        }
    }
}

enum class IntervalPolicyOfRemoteData(val value: Long) {
    //DAYLY(86400000L),
    //EACH_HOUR(3600000L),
    EACH_FIVE_MINUTES(300000L),
    //EACH_MINUTE(60000L)

}
