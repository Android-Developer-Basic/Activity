package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class FillFormContract : ActivityResultContract<String, UserModel?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserModel? {
        if (resultCode != Activity.RESULT_OK) return null

        return intent?.getParcelableExtra("USER")
    }
}
