import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import otus.gpb.homework.activities.EditProfileActivity
import otus.gpb.homework.activities.FillFormActivity
import otus.gpb.homework.activities.User
import kotlin.contracts.Returns

class MyActivityContract : ActivityResultContract<Int, User?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, FillFormActivity::class.java)

    }

    override fun parseResult(resultCode: Int, intent: Intent?) : User?{
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return intent?.getParcelableExtra("outPut")
    }
}