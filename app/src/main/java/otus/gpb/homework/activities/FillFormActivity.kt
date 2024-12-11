package otus.gpb.homework.activities

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

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            val data = getDataFromEditText()
            val intent = Intent(this, EditProfileActivity::class.java).putExtras(data)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun getDataFromEditText(): Bundle {
        val bundle = Bundle()
        bundle.putString("name", findViewById<EditText>(R.id.name_text_input).text.toString());
        bundle.putString(
            "surname",
            findViewById<EditText>(R.id.surname_text_input).text.toString()
        );
        bundle.putString("age", findViewById<EditText>(R.id.age_text_input).text.toString());
        return bundle
    }
}