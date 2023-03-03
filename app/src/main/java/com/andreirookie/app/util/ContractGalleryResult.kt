package com.andreirookie.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class ContractGalleryResult : ActivityResultContract<String, Uri?>() {
    override fun createIntent(context: Context, input: String): Intent =
        Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType(input)

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) intent?.data else null
    }
}