package otus.gpb.homework.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Payload(
    val firstname: String,
    val lastname: String,
    val age: String,
    val photo: @RawValue Bitmap? = null,
) : Parcelable
