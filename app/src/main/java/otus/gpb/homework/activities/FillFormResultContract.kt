package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class FillFormResultContract :
  ActivityResultContract<FillFormActivity.EditData, FillFormActivity.EditData?>() {
  override fun createIntent(
    context: Context,
    input: FillFormActivity.EditData,
  ): Intent = FillFormActivity.getIntentToStartForResult(context, input)

  override fun parseResult(
    resultCode: Int,
    intent: Intent?,
  ): FillFormActivity.EditData? {
    if (intent == null || resultCode != AppCompatActivity.RESULT_OK) {
      return null
    }

    return FillFormActivity.getEditDataFromIntent(intent)
  }
}