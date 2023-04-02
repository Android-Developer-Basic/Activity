package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ContractEditProfileAndFillForm : ActivityResultContract<PersonDTO, PersonDTO?>() {
    companion object {
        const val KEY = "personKey"
    }

    override fun createIntent(context: Context, input: PersonDTO): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            putExtra(KEY, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PersonDTO? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        intent?.let {
           return it.getParcelableExtra(KEY) }
        return null
    }

}