package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val name:String, val lastName:String, val age:String?) : Parcelable