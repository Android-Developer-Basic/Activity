package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class ProfileDataContract : ActivityResultContract<ProfileData, ProfileData?>() {
    override fun createIntent(context: Context, input: ProfileData): Intent =
        Intent(context, FillFormActivity::class.java).apply {
            putExtra("data", input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): ProfileData? =
        intent
            .takeIf { resultCode == AppCompatActivity.RESULT_OK }
            ?.getParcelableExtra("data", ProfileData::class.java)
}