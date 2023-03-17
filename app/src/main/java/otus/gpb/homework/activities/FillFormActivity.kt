package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import Profile
import android.content.Intent
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val etName = findViewById<EditText>(R.id.edittext_name)
        val etSurname = findViewById<EditText>(R.id.edittext_surname)
        val etAge = findViewById<EditText>(R.id.edittext_age)

        intent.extras?.getParcelable<Profile>("profile")?.let {
            etName.setText(it.name)
            etSurname.setText(it.surname)
            etAge.setText(it.age.toString())
        }

        findViewById<Button>(R.id.button_apply).apply {
            setOnClickListener{
                setResult(
                    RESULT_OK,
                    Intent().apply {

                        var age = etAge.text.toString().toInt()
                        if (age !in 0..130) age = 0

                        putExtra(
                            "profile",
                            Profile(etName.text.toString(), etSurname.text.toString(), age.toUByte())
                        )
                    }
                )
                finish()
            }
        }

    }
}