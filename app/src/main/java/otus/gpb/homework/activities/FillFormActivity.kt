package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstNameInput = findViewById<EditText>(R.id.name_input)
        val lastNameInput = findViewById<EditText>(R.id.sur_name_input)
        val ageInput = findViewById<EditText>(R.id.age_input)

        intent?.getParcelableExtra<DataProfile>("data", DataProfile::class.java)?.apply {
            firstNameInput.setText(name)
            lastNameInput.setText(surname)
            ageInput.setText(age)
        }

        findViewById<Button>(R.id.apply_button).setOnClickListener {
            val intent = Intent().apply {
                putExtra(
                    "data", DataProfile(
                        firstNameInput.text.toString(),
                        lastNameInput.text.toString(),
                        ageInput.text.toString()
                    )
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}