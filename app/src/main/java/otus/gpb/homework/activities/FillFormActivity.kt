package otus.gpb.homework.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<AppCompatButton>(R.id.button_ok).setOnClickListener {
            var view = findViewById<AppCompatEditText>(R.id.edittext_name)
            var text = view.text.toString()
            intent.putExtra(EditProfileActivity.PROFILE_NAME, text)

            view = findViewById(R.id.edittext_surname)
            text = view.text.toString()
            intent.putExtra(EditProfileActivity.PROFILE_SURNAME, text)

            view = findViewById(R.id.edittext_age)
            text = view.text.toString()
            intent.putExtra(EditProfileActivity.PROFILE_AGE, text)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}