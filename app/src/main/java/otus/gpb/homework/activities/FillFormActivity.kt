package otus.gpb.homework.activities

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class FillFormActivity : AppCompatActivity() {
    private lateinit var name: AppCompatEditText
    private lateinit var surname: AppCompatEditText
    private lateinit var age: AppCompatEditText

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        name = findViewById(R.id.edit_name)
        surname = findViewById(R.id.edit_surname)
        age = findViewById(R.id.edit_age)

        name.text = intent.getStringExtra("PROFILE_NAME").toString().toEditable()
        surname.text = intent.getStringExtra("PROFILE_SURNAME").toString().toEditable()
        age.text = intent.getStringExtra("PROFILE_AGE").toString().toEditable()

        findViewById<AppCompatButton>(R.id.set_button).setOnClickListener {
            intent.putExtra("PROFILE_NAME", name.text.toString())
            intent.putExtra("PROFILE_SURNAME", surname.text.toString())
            intent.putExtra("PROFILE_AGE", age.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}