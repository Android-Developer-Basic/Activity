package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf

class FillFormActivity : AppCompatActivity() {
    lateinit var editTextName: EditText
    lateinit var editTextSurname: EditText
    lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editTextName = findViewById(R.id.name)
        editTextSurname = findViewById(R.id.surname)
        editTextAge = findViewById(R.id.age)

        editTextName.setText(intent.getStringExtra("name") ?: "")
        editTextSurname.setText(intent.getStringExtra("surname") ?: "")
        editTextAge.setText(intent.getStringExtra("age") ?: "")

        findViewById<Button>(R.id.apply).setOnClickListener {
            val intent = Intent().apply {
                putExtra("name", editTextName.text.toString())
                putExtra("surname", editTextSurname.text.toString())
                putExtra("age", editTextAge.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    class Contract : ActivityResultContract<Map<String, String>, Map<String, String>?>() {
        override fun createIntent(context: Context, input: Map<String, String>) =
            Intent(context, FillFormActivity::class.java).apply {
                putExtra("name", input["name"])
                putExtra("surname", input["surname"])
                putExtra("age", input["age"])
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Map<String, String>? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null
            return mapOf(
                Pair("name", intent.getStringExtra("name") ?: ""),
                Pair("surname", intent.getStringExtra("surname") ?: ""),
                Pair("age", intent.getStringExtra("age") ?: "")
            )
        }
    }
}