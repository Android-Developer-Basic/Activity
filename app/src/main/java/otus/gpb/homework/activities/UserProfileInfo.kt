package otus.gpb.homework.activities

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileInfo(

    val firstName: String,

    val lastName: String,

    val age: Int = 0,

    var uri: Uri? = null,

): Parcelable
