package otus.gpb.homework.activities.receiver

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmDTO(
    val title: String,
    val year: String,
    val description: String
): Parcelable