package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import otus.gpb.homework.model.Payload

class FillFormActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fill_form_activity)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            setResult(
                RESULT_OK, Intent().putExtra(
                    "payload",
                    Payload(
                        findViewById<EditText>(R.id.firstname_input).text.toString(),
                        findViewById<EditText>(R.id.lastname_input).text.toString(),
                        findViewById<EditText>(R.id.ageInput).text.toString(),
                    )
                )
            )

            finish()
        }
    }
}
