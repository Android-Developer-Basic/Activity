package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InformationUserDTO (
    val first_name: String,
    val last_name: String,
    val age: String
    ): Parcelable