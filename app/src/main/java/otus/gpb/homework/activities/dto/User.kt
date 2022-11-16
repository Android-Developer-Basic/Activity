package otus.gpb.homework.activities.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val age: String,
    ):Parcelable
