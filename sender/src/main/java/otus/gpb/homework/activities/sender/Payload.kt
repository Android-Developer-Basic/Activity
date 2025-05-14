package otus.gpb.homework.activities.sender

import java.io.Serializable

data class Payload(
    val title: String,
    val year: String,
    val description: String
) : Serializable