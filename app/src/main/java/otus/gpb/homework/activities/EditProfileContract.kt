package otus.gpb.homework.activities

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

const val USER_ID: String = "id123"

class EditProfileContract : ActivityResultContract<Unit, UserProfileInfo?>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserProfileInfo? {
        if (resultCode != RESULT_OK)
            return null
        return intent?.extras?.getParcelable(USER_ID)
    }
}