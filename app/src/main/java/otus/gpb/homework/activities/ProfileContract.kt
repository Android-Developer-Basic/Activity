package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.content.IntentCompat

class ProfileContract : ActivityResultContract<Unit, User?>() {

    override fun createIntent(
        context: Context,
        input: Unit
    ): Intent {
        return Intent(context, FillFormActivity::class.java)
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): User? {
        if (
            intent == null
            || resultCode == RESULT_CANCELED
            || resultCode != RESULT_OK
        ) return null

        return IntentCompat.getParcelableExtra(
            intent,
            FillFormActivity.PROFILE_RESULT_KEY,
            User::class.java
        )
    }
}