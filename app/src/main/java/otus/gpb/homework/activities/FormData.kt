package otus.gpb.homework.activities

import android.os.Parcelable
import android.text.Editable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormData(
    val name: String,
    val surName: String, val year: String): Parcelable
