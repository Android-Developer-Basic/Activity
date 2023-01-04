package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {

    lateinit var binding: ActivityFillFormBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val r =  Intent()

            r.putExtra("age", binding.age.text.toString())
            r.putExtra("textviewName", binding.name.text.toString())
            r.putExtra("textviewSurname", binding.LastName.text.toString())

            setResult(RESULT_OK, r)

            finish()

        }
    }

}