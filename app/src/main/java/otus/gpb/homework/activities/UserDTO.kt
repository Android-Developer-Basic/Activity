package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserDTO(
    val name: String,
    val secondName: String,
    val age: Int
) : Parcelable
