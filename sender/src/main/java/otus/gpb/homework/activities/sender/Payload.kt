package otus.gpb.homework.activities.sender

data class Payload(
    val title: String,
    val year: String,
    val description: String
) {
    companion object {
        const val MOVIE_TITLE = "MOVIE_NAME"
        const val MOVIE_YEAR = "MOVIE_YEAR"
        const val MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION"
    }
}