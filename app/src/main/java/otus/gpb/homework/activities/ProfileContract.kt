package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class ProfileContract : ActivityResultContract<DataProfile, DataProfile?>() {
    override fun createIntent(context: Context, input: DataProfile): Intent =
        Intent(context, FillFormActivity::class.java).apply {
            putExtra("data", input)
        }


    override fun parseResult(resultCode: Int, intent: Intent?): DataProfile? =
        intent
            .takeIf { resultCode == AppCompatActivity.RESULT_OK }
            ?.getParcelableExtra("data", DataProfile::class.java)

}