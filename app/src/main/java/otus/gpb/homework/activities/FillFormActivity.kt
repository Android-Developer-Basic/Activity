package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<EditText>(R.id.ET_name)
        val surname = findViewById<EditText>(R.id.ET_surname)
        val age = findViewById<EditText>(R.id.ET_age)

        findViewById<Button>(R.id.btn_apply).setOnClickListener {
            val intent = Intent().apply {
                val user = DTOuser (name.text.toString(), surname.text.toString(), age.text.toString())
                putExtra("UserData", user)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
}