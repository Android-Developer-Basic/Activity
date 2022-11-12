package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(val firstName: String, val lastName: String, val age: Int) : Parcelable
