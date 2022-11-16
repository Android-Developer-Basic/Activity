package dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(
    val title: String,
    val year: String,
    val description: String): Parcelable