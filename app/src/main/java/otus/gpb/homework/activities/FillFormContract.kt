package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract


class FillFormContract : ActivityResultContract<UserInfo, UserInfo?>() {

    override fun createIntent(context: Context, input: UserInfo): Intent {
        return Intent(context, FillFormActivity::class.java)
            .putExtra("USER_INFO", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserInfo? {
        return when(resultCode)
        {
            Activity.RESULT_OK -> intent?.extras?.getParcelable("USER_INFO")
            else -> null
        }
    }

}