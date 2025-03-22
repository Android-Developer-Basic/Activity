package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.EditProfileActivity.ProfileOptions.ProfileInfo

class ContractFillProfile: ActivityResultContract<ProfileInfo, ProfileInfo?>() {

    override fun createIntent(context: Context, input: ProfileInfo): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra(KEY_FILL_PROFILE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ProfileInfo? {
        when {
            intent == null || resultCode != AppCompatActivity.RESULT_OK -> return null
        }

        @Suppress("DEPRECATION")
        return intent?.extras?.getSerializable(KEY_RESULT_FILL_PROFILE) as ProfileInfo
    }

    companion object {
        const val KEY_FILL_PROFILE = "fill_profile"
        const val KEY_RESULT_FILL_PROFILE = "fill_profile_result"
    }

}