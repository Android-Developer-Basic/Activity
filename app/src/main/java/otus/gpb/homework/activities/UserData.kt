package otus.gpb.homework.activities

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class UserDataSet(
    var firstname: String = "",
    var lastname: String = "",
    var birthdate: LocalDate = LocalDate.of(1900,1,1),
    var avatar: Uri = Uri.EMPTY
):Parcelable
