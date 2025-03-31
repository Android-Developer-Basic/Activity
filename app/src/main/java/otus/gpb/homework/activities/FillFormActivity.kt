package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

const val NAME_KEY = "name_key"
const val LASTNAME_KEY = "lastname_key"
const val AGE_KEY = "age_key"

class FillFormActivity : AppCompatActivity() {
    private lateinit var okButton: Button
    private lateinit var nameTe: AppCompatEditText
    private lateinit var lastnameTe: AppCompatEditText
    private lateinit var ageTe: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        okButton = findViewById(R.id.ok_btn)
        nameTe = findViewById(R.id.name_ed)
        lastnameTe = findViewById(R.id.lastname_ed)
        ageTe = findViewById(R.id.age_ed)

        okButton.setOnClickListener {
            handleOkButtonClick()
        }
    }

    private fun handleOkButtonClick() {
        val name = nameTe.text.toString()
        val lastname = lastnameTe.text.toString()
        val age = ageTe.text.toString()

        if (name != "" || lastname != "" || age != "") {
            val intent = Intent().apply {
                putExtra(NAME_KEY, name)
                putExtra(LASTNAME_KEY, lastname)
                putExtra(AGE_KEY, age)
            }

            setResult(RESULT_OK, intent)
        } else {
            val intent = Intent()

            setResult(RESULT_CANCELED, intent)
        }

        finish()
    }
}