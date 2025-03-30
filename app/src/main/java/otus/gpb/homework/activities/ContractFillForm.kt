package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class ContractFillForm(): ActivityResultContract<Person, Person?>() {
    override fun createIntent(context: Context, input: Person): Intent {
        val intent = Intent(context, FillFormActivity::class.java).apply {
            putExtra(KEY_PERSON, input)
        }
        return intent
    }


    override fun parseResult(resultCode: Int, intent: Intent?): Person? {
        when {
            resultCode == AppCompatActivity.RESULT_CANCELED -> return null
            intent == null || resultCode != AppCompatActivity.RESULT_OK -> return null
        }
        return intent?.getParcelableExtra(RESULT_KEY)
    }

    companion object {
        const val KEY_PERSON = "key_person"
        const val RESULT_KEY = "result_key"
    }

}


