package otus.gpb.homework.activities.mylibrary2

import java.io.Serializable

data class Payload(
    val title: String,
    val year: String,
    val description: String
) : Serializable
