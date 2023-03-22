package otus.gpb.homework.activities

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val name : String,
    val surname : String,
    val age : String,
    var picUri : Uri?
) : Parcelable
