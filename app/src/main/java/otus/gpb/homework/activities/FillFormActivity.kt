package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : Activity() {

    private lateinit var textName: EditText
    private lateinit var textSurname: EditText
    private lateinit var textAge: EditText
    private lateinit var buttonOk: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        textName = findViewById(R.id.editTextName)
        textSurname = findViewById(R.id.editTextSurname)
        textAge = findViewById(R.id.editTextAge)

        buttonOk = findViewById(R.id.buttonOk)
        buttonCancel = findViewById(R.id.buttonCancel)

        buttonOk.setOnClickListener {
            val intent = Intent()
            intent.putExtra("name", textName.text.toString())
            intent.putExtra("surname", textSurname.text.toString())
            intent.putExtra("age", textAge.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
        buttonCancel.setOnClickListener { finish() }
    }
}