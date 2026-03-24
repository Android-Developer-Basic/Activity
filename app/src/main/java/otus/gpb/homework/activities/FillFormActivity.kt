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

    companion object {
        const val EXTRA_FIRSTNAME = "firstname"
        const val EXTRA_SURNAME = "surname"
        const val EXTRA_BIRTHDAY = "birthday"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)

        setupInsets()
        initViews()
        setupListeners()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    private fun initViews() {
        applyButton = findViewById(R.id.Apply_button)
        firstName = findViewById(R.id.Firstname_input)
        surName = findViewById(R.id.Surname_input)
        birthday = findViewById(R.id.Birthday_input)
    }

    private fun setupListeners() {
        applyButton.setOnClickListener {
            sendResult()
        }
    }

    private fun sendResult() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_FIRSTNAME, firstName.text.toString())
            putExtra(EXTRA_SURNAME, surName.text.toString())
            putExtra(EXTRA_BIRTHDAY, birthday.text.toString())
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}