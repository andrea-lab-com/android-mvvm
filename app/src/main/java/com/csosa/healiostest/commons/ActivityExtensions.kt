package com.csosa.healiostest.commons

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.csosa.healiostest.R

internal fun Activity.showSnackBar(view: View, message: String, isError: Boolean = false) {

    val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    if (isError) {

        sb.setBackgroundTint(loadColor(R.color.colorError))
            .setTextColor(loadColor(R.color.colorOnError))
            .show()
    } else {

        sb.setBackgroundTint(loadColor(R.color.colorSecondary))
            .setTextColor(loadColor(R.color.colorOnSecondary))
            .show()
    }
}
