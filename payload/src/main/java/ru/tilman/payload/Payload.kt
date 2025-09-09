package ru.tilman.payload

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


const val PAYLOAD_KEY = "payload_key"

@Parcelize
data class Payload(
    val title: String,
    val year: String,
    val description: String
) : Parcelable