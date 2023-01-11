package otus.gpb.homework.activities

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract: ActivityResultContract <String, User?> () {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return intent.extras?.getParcelable("user")
    }
}