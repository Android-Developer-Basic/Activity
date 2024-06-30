package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract


class FillFormContract : ActivityResultContract<UserDTO?, UserDTO?>() {
    override fun createIntent(context: Context, input: UserDTO?): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            if (input == null) return@apply
            putExtra("data", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserDTO? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return intent.getParcelableExtra("data")
    }
}
