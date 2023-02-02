package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonApply.setOnClickListener {
            val userInfo = InformationUserDTO(
                first_name = binding.textviewFirstName.text.toString(),
                last_name = binding.textviewLastName.text.toString(),
                age = binding.textviewAge.text.toString())
            val intent = Intent().apply {
                putExtra("UserInfo", userInfo)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        if (binding.textviewAge.equals("") and binding.textviewFirstName.equals("") and binding.textviewLastName.equals("")){
            super.onBackPressed()
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.back_fill_form_title))
                .setMessage(resources.getString(R.string.back_fill_form_message))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                }
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                    super.onBackPressed()
                }
                .show()
        }
    }
}