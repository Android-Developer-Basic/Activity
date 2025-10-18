package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FillFormActivity : AppCompatActivity() {

    private lateinit var applyButton: Button
    private lateinit var firstName: EditText
    private lateinit var surName: EditText
    private lateinit var birthday: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        applyButton = findViewById( R.id.Apply_button)
        firstName   = findViewById( R.id.Firstname_input )
        surName     = findViewById( R.id.Surname_input )
        birthday    = findViewById( R.id.Birthday_input )
        applyButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("firstname", firstName.text.toString() )
            resultIntent.putExtra("surname"  , surName.text.toString() )
            resultIntent.putExtra("birthday"  , birthday.text.toString() )
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}