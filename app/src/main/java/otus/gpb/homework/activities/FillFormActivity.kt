package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFillFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userData = getIntent().extras?.getParcelable(KEY_DATA, UserData::class.java)
        if (userData != null) {
            binding.editName.setText(userData.name)
            binding.editSurname.setText(userData.surname)
            binding.editAge.setText(userData.age)
        }
        binding.button.setOnClickListener(){
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.apply {
                putExtra(KEY_NAME, binding.editName.text.toString())
                putExtra(KEY_SURNAME, binding.editSurname.text.toString())
                putExtra(KEY_AGE, binding.editAge.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    companion object {
        const val KEY_DATA = "userData"
        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_AGE = "age"
    }
}