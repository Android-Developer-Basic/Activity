package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class FillFormActivity : AppCompatActivity() {

    private lateinit var textview_name: TextInputEditText
    private lateinit var textview_surname: TextInputEditText
    private lateinit var textview_age: TextInputEditText
    private lateinit var button5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textview_name = findViewById(R.id.textview_name)
        textview_surname = findViewById(R.id.textview_surname)
        textview_age = findViewById(R.id.textview_age)
        button5 = findViewById(R.id.button5)


        val intent = intent.getParcelableExtra<Person>(ContractFillForm.KEY_PERSON)
        textview_name.setText(intent?.firstName)
        textview_surname.setText(intent?.lastName)
        textview_age.setText(intent?.age.toString())

        button5.setOnClickListener {

            if (textview_name.text!!.isNotBlank()  &&
                textview_surname.text!!.isNotBlank()
                && textview_age.text!!.isNotBlank()) {

                val peopleResult = Person(
                    textview_name.text.toString(),
                    textview_surname.text.toString(),
                    textview_age.text.toString().toInt())

                val intent = Intent().putExtra(ContractFillForm.RESULT_KEY, peopleResult)
                setResult(RESULT_OK, intent)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }


    }
}