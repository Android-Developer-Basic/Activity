package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class ContractFillFormActivity : ActivityResultContract<PersonData, PersonData?>() {
    override fun createIntent(context: Context, input: PersonData): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            putExtra("name", input.name)
            putExtra("surName", input.surName)
            putExtra("age", input.age)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PersonData? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return PersonData(
            intent.getStringExtra("name"),
            intent.getStringExtra("surName"),
            intent.getIntExtra("age", -1)
        )
    }
}