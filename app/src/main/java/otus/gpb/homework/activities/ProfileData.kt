package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileData(
    val firstName: String,
    val lastName: String,
    val age: String
) : Parcelable
