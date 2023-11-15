package otus.gpb.homework.activities.sender

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

public data class Payload(
    val title: String,
    val year: String,
    val description: String,
    val image: String,
):Serializable
