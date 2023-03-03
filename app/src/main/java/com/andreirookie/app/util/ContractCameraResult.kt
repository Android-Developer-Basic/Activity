package com.andreirookie.app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class ContractCameraResult : ActivityResultContract<Void, Uri?>() {
    override fun createIntent(context: Context, input: Void): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//        return intent.takeIf { resultCode == Activity.RESULT_OK  }?.getParcelableExtra("data")
        return intent.takeIf { resultCode == Activity.RESULT_OK  }?.data
    }
}