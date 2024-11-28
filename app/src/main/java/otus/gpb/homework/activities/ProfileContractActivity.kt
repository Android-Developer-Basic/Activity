package otus.gpb.homework.activities

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

const val RESULT_KEY = "result_key"

class ProfileContractActivity : ActivityResultContract<Unit, Profile?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Profile? {
        if (resultCode != RESULT_OK)
            return null
        return intent?.extras?.getParcelable(RESULT_KEY)
    }
}
