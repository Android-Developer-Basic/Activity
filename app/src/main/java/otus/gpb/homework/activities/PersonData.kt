package otus.gpb.homework.activities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonData(
    var name: String? = "DefaultName",
    var surName: String? = "DefaultSurname",
    var age: Int = -1
) : Parcelable