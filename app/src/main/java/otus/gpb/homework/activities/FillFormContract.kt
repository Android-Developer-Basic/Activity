package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract

class FillFormContract : ActivityResultContract<UserDTO, UserDTO?>() {

    override fun createIntent(context: Context, input: UserDTO): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra("userDTO", input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserDTO? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("userDTO", UserDTO::class.java)
        } else {
            intent.extras?.getParcelable("userDTO")
        }
    }
}