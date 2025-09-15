package otus.gpb.homework.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val age: Int,
    ) : Parcelable
