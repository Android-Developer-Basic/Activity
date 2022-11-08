package dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDescriptionDTO(
    val title: String,
    val year: Int,
    val description: String): Parcelable
