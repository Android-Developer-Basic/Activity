package com.andreirookie.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.andreirookie.app.EditProfileActivity
import com.andreirookie.app.FillFormActivity
import com.andreirookie.app.dto.User

class ContractEditProfileAndFillForm : ActivityResultContract<User, User?>() {
    override fun createIntent(context: Context, input: User): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            putExtra(EditProfileActivity.EXTRA_USER, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): User? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null
        return intent.getParcelableExtra(EditProfileActivity.EXTRA_USER)
    }
}