package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonDTO (
    var name: String?,
    var lastName: String?,
    var age: String?
) : Parcelable