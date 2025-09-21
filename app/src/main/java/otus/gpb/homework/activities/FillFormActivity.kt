package otus.gpb.homework.activities

import android.app.Activity
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

        val nameField = findViewById<EditText>(R.id.name)
        val surnameField = findViewById<EditText>(R.id.surname)
        val ageField = findViewById<EditText>(R.id.age)
        val getDataBtn = findViewById<Button>(R.id.btn_get)

        getDataBtn.setOnClickListener {
            val name = nameField.text?.toString() ?: ""
            val surname = surnameField.text?.toString() ?: ""
            val age = ageField.text?.toString() ?: ""

            val bundle = Bundle()
            bundle.apply {
                putString("name", name)
                putString("surname", surname)
                putString("age", age)
            }

            val intent = Intent()
            intent.putExtra("BundleTitle", bundle)

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

    }
}