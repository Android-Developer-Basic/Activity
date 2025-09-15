package otus.gpb.homework.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import otus.gpb.homework.activities.FillFormActivity
import otus.gpb.homework.model.User

class ContractForFillFormActivity : ActivityResultContract<User?, User?>() {
    override fun createIntent(
        context: Context,
        input: User?
    ): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra(EDIT_PROFILE_TO_FILL_FORM_KEY, input)
        return intent
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): User? {
        if (
            resultCode == AppCompatActivity.RESULT_CANCELED
            || intent == null
            || resultCode != AppCompatActivity.RESULT_OK
        ) return null

        val user =
            IntentCompat.getParcelableExtra(intent, FILL_FORM_KEY_RESULT_KEY, User::class.java)
        return user
    }

    companion object {
        const val EDIT_PROFILE_TO_FILL_FORM_KEY = "edit_profile_to_fill_form"
        const val FILL_FORM_KEY_RESULT_KEY = "fill_form_key_result"
    }
}