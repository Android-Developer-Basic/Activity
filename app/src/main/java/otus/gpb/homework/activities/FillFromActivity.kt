package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FillFromActivity : AppCompatActivity(R.layout.activity_fill_from) {

    private lateinit var editTextName: EditText
    private lateinit var editTextSurName: EditText
    private lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editTextName = findViewById(R.id.editTextName)
        editTextSurName = findViewById(R.id.editTextSurName)
        editTextAge = findViewById(R.id.editTextAge)

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            if (editTextName.text.isNullOrEmpty() ||
                editTextSurName.text.isNullOrEmpty() ||
                editTextAge.text.isNullOrEmpty()){
                showError()
            }
            else {
                intent = Intent()
                    .putExtra(NAME_KEY, editTextName.text.toString())
                    .putExtra(SUR_NAME_KEY, editTextSurName.text.toString())
                    .putExtra(AGE_KEY, editTextAge.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun showError(){
        Toast.makeText(this, getString(R.string.errorValid), Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }
}