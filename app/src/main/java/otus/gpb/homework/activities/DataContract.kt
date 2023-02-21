package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class DataContract: ActivityResultContract<String,String?>() {
    override fun createIntent(context: Context, input: String): Intent {

    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        TODO("Not yet implemented")
    }
}