package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFillFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillFormBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.applyBtn.setOnClickListener {
            val firstName = binding.firstnameField.text.toString()
            val lastName = binding.lastnameField.text.toString()
            val age = binding.ageField.text.toString().toInt()

            val user = UserModel(firstName, lastName, age)
            val intent = Intent().apply {
                putExtra("USER", user)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
