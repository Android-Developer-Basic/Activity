
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile (val name: String = "", val surname: String = "", val age: UByte = 0u): Parcelable