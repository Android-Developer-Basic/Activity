package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract: ActivityResultContract<String, Intent?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        return intent
    }
}