package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<AppCompatButton>(R.id.set_button).setOnClickListener {
            intent.putExtra("PROFILE_NAME", findViewById<AppCompatEditText>(R.id.edit_name).text.toString())
            intent.putExtra("PROFILE_SURNAME", findViewById<AppCompatEditText>(R.id.edit_surname).text.toString())
            intent.putExtra("PROFILE_AGE", findViewById<AppCompatEditText>(R.id.edit_age).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}