package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.apply_btn).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.apply_btn -> sendResult()
        }
    }

    private fun sendResult() {
        val nameText = findViewById<EditText>(R.id.name_edit_text).text
        val lastNameText = findViewById<EditText>(R.id.last_name_edit_text).text
        val ageText = findViewById<EditText>(R.id.age_edit_text).text

        val intent = Intent().apply {
            putExtra(nameExtra, nameText.toString())
            putExtra(lastnameExtra, lastNameText.toString())
            putExtra(ageExtra, ageText.toString())
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}