package otus.gpb.homework.activities
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null
) : Parcelable