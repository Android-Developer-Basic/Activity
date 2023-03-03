package com.andreirookie.app.dto

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long = 0L,
    val name: String,
    val surname: String,
    val age: Int,
    val image: Uri? = null
) : Parcelable
