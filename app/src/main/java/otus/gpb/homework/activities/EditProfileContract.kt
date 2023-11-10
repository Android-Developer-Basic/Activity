package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity


class EditProfileContract :
    ActivityResultContract<Map<EDIT_FIELD, CharSequence>, Map<EDIT_FIELD, CharSequence>?>() {
    override fun createIntent(context: Context, input: Map<EDIT_FIELD, CharSequence>): Intent {

        val intent = Intent(context, FillFormActivity::class.java).also { intent ->
            input.forEach {
                intent.putExtra(it.key.toString(), it.value)
            }
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Map<EDIT_FIELD, CharSequence>? {
        if (intent == null || resultCode != AppCompatActivity.RESULT_OK) return null
        val name = intent.extras?.getCharSequence(EDIT_FIELD.NAME.toString()) ?: ""
        val surname = intent.extras?.getCharSequence(EDIT_FIELD.SURNAME.toString()) ?: ""
        val age = intent.extras?.getCharSequence(EDIT_FIELD.AGE.toString()) ?: ""
        return mapOf(
            EDIT_FIELD.NAME to name,
            EDIT_FIELD.SURNAME to surname,
            EDIT_FIELD.AGE to age,
        )
    }
}