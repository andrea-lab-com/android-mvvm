package com.csosa.healiostest.commons

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

internal fun Context.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

internal inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}
