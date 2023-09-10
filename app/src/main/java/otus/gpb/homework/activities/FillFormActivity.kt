package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {

    lateinit var binding: ActivityFillFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillFormBinding.inflate(layoutInflater)
        binding.root
        setContentView(binding.root)
        binding.applyButton.setOnClickListener {
            val i = Intent()
            i.putExtra("name", binding.nameEditText.text.toString())
            i.putExtra("lastName", binding.lastNameEditText.text.toString())
            i.putExtra("age", binding.ageEditText.text.toString())
            setResult(RESULT_OK, i)
            finish()
        }
    }
}