package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var saveBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        editText1 = findViewById(R.id.firstText)
        editText2 = findViewById(R.id.secondText)
        editText3 = findViewById(R.id.thirdText)
        saveBtn = findViewById(R.id.saveNewProfileBtn)

        val name = intent.extras?.getCharSequence(EDIT_FIELD.NAME.toString())
        val surname = intent.extras?.getCharSequence(EDIT_FIELD.SURNAME.toString())
        val age = intent.extras?.getCharSequence(EDIT_FIELD.AGE.toString())

        editText1.setText(name)
        editText2.setText(surname)
        editText3.setText(age)

        saveBtn.setOnClickListener {
            intent = Intent().apply {
                putExtra(EDIT_FIELD.NAME.toString(), editText1.text)
                putExtra(EDIT_FIELD.SURNAME.toString(), editText2.text)
                putExtra(EDIT_FIELD.AGE.toString(), editText3.text)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}