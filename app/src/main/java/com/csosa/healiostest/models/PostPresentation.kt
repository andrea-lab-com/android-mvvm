package com.csosa.healiostest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class PostPresentation(val id: Int,
                                     val title: String,
                                     val body: String,
                                     val userId:Int) : Parcelable
