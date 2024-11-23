package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

const val KEY_NAME = "key_name"
const val KEY_SURNAME = "key_surname"
const val KEY_AGE = "key_age"

class FillFormContract : ActivityResultContract<String, ProfileInfo?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ProfileInfo? {
        when {
            resultCode == AppCompatActivity.RESULT_CANCELED -> return null
            intent == null || resultCode != AppCompatActivity.RESULT_OK -> return null
        }

        return ProfileInfo(intent!!.getStringExtra(KEY_NAME), intent.getStringExtra(KEY_SURNAME),  intent.getStringExtra(KEY_AGE))
    }
}