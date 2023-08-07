package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditProfileFillFromContract : ActivityResultContract<ManInfo, ManInfo?>(){
    override fun createIntent(context: Context, input: ManInfo): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            putExtra("manInfo", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ManInfo? {
        if (intent==null) return null
        if (resultCode!=Activity.RESULT_OK) return null
        return intent.getSerializableExtra("resultManInfo") as ManInfo
    }
}