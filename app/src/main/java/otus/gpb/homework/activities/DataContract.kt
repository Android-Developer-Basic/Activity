package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class DataContract: ActivityResultContract<User,String?>() {
    override fun createIntent(context: Context, input:User): Intent {
        return Intent(context, FillFormActivity::class.java).apply{
            putExtra("name", input.name)
            putExtra("surname", input.surname)
            putExtra("age", input.age)
        }

    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if(intent == null) return null
        if(resultCode != Activity.RESULT_OK) return null
        return intent.getStringExtra("result")
    }
}