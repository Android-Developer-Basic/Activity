package otus.gpb.homework.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonDTO(
    var firstName: String?,
    var lastName: String?,
    var age: Int?
) : Parcelable