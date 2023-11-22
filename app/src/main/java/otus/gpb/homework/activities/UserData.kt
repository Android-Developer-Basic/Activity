package otus.gpb.homework.activities

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.Period

@Parcelize
data class UserDataSet(
    var firstname: String = "",
    var lastname: String = "",
    var birthdate: LocalDate = LocalDate.of(1900,1,1),
    var avatar: Uri = Uri.EMPTY
):Parcelable {
    override fun toString(): String {
        var rc = ""
        if (firstname.isNotEmpty()) {
            rc += "FirstName: $firstname"
        }

        if (lastname.isNotEmpty()) {
            if (rc.isNotEmpty()) {
                rc += ", "
            }
            rc += "LastName: $lastname"
        }

        val age=getAge()
        if (age.isNotEmpty()) {
            if (rc.isNotEmpty()) {
                rc += ", "
            }
            rc += "Age: ${getAge()}"
        }
        return rc
    }
    fun getAge():String {
        if (birthdate.year != 1900) {
            return Period.between(birthdate, LocalDate.now()).years.toString()
        }
        return ""
    }
}
