package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.contracts.ContractForFillFormActivity.Companion.EDIT_PROFILE_TO_FILL_FORM_KEY
import otus.gpb.homework.contracts.ContractForFillFormActivity.Companion.FILL_FORM_KEY_RESULT_KEY
import otus.gpb.homework.model.User

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
        val user: User? =
            IntentCompat.getParcelableExtra(intent, EDIT_PROFILE_TO_FILL_FORM_KEY, User::class.java)
        Log.d("debug_granted", "user: $user")
        val firstName = findViewById<EditText>(R.id.editTextFirstName)
        val lastName = findViewById<EditText>(R.id.editTextLastName)
        val age = findViewById<EditText>(R.id.editTextAge)

        user?.let {
            firstName.setText(it.firstName)
            lastName.setText(it.lastName)
            age.setText(it.lastName)
        }

        findViewById<Button>(R.id.apply_button)?.setOnClickListener {
            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()
            val age = runCatching { age.text.toString().toInt() }.getOrDefault(0)

            if (firstName.isNotBlank() && lastName.isNotBlank() && age > 0) {
                val user = User(firstName, lastName, age)
                val intent = Intent().putExtra(FILL_FORM_KEY_RESULT_KEY, user)
                setResult(RESULT_OK, intent)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }


    }


}