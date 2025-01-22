package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity


const val KEY_PROFILE = "key_profile"
const val KEY_RESULT = "key_result"

const val KEY_NAME = "key_name"
const val KEY_SURNAME = "key_surname"
const val KEY_AGE = "key_age"


class ContractFillFormActivity : ActivityResultContract<Profile, Profile?>() {

    override fun createIntent(context: Context, input: Profile): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra(KEY_PROFILE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Profile? {
        when {
            resultCode == AppCompatActivity.RESULT_CANCELED -> return null
        }

        val profile = Profile(
            name = intent?.extras?.getString(KEY_NAME).toString(),
            surname = intent?.extras?.getString(KEY_SURNAME).toString(),
            age = intent?.extras?.getString(KEY_AGE).toString()
        )

        return profile
    }
}