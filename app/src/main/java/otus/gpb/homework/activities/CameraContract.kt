package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider

class CameraContract(): ActivityResultContract<Void?, Void?>() {

    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply{
            userPhotoUri = FileProvider.getUriForFile(context, IMAGE_PACKAGE, imgFile!!)
            putExtra(MediaStore.EXTRA_OUTPUT, userPhotoUri)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?):Void?{

        return null
    }
}