package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class ContractFillFormActivity: ActivityResultContract<UserData, UserData?>() {
    override fun createIntent(
        context: Context,
        input: UserData
    ): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra(FillFormActivity.KEY_DATA, input)
        return intent
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): UserData? {
        when {
            resultCode == AppCompatActivity.RESULT_CANCELED -> return null
            intent == null -> return null
        }
        val userData = UserData(
            intent?.extras?.getString(FillFormActivity.KEY_NAME).toString(),
            intent?.extras?.getString(FillFormActivity.KEY_SURNAME).toString(),
            intent?.extras?.getString(FillFormActivity.KEY_AGE).toString(),
        )
        return userData
    }
}