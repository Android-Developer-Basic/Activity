package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import otus.gpb.homework.activities.dto.User


class CustomActivityResultContract: ActivityResultContract<String, User?>() {
    override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        if (intent == null) return null
        return intent.getParcelableExtra(USER_DTO_EXTRA)
    }

    companion object {
        const val USER_DTO_EXTRA = "UserDTO"
    }
}
