package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        findViewById<Button>(R.id.apply)?.setOnClickListener {
            val intent = Intent()
                .putExtra(KEY_NAME, findViewById<EditText>(R.id.username).text.toString())
                .putExtra(KEY_SURNAME, findViewById<EditText>(R.id.lastname).text.toString())
                .putExtra(KEY_AGE, findViewById<EditText>(R.id.age).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}