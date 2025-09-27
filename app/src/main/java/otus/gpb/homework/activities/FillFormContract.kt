package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat

class FillFormContract : ActivityResultContract<Unit, UserInfo?>() {
    override fun createIntent(
        context: Context,
        input: Unit
    ): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): UserInfo? {
        if (
            resultCode == AppCompatActivity.RESULT_CANCELED
            || intent == null
            || resultCode != AppCompatActivity.RESULT_OK
        ) return null

        return IntentCompat.getParcelableExtra(
            intent,
            FillFormActivity.RESULT_KEY,
            UserInfo::class.java
        )

    }
}