package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class FillFormActivity : AppCompatActivity(R.layout.activity_fill_form) {
  private val etName: EditText by lazy { findViewById(R.id.etName) }
  private val etSurname: EditText by lazy { findViewById(R.id.etSurname) }
  private val etAge: EditText by lazy { findViewById(R.id.etAge) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).apply {
      setSupportActionBar(this)

      supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(true)
        it.setDisplayShowHomeEnabled(true)
      }
    }

    findViewById<Button>(R.id.btnAccept).setOnClickListener {
      accept()
    }

    getEditDataFromIntent(intent)?.let { editData: EditData ->
      etName.setText(editData.name)
      etSurname.setText(editData.surname)
      etAge.setText(editData.age)
    } ?: run {
      finish()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
    }

    return super.onOptionsItemSelected(item)
  }

  private fun accept() {
    val responseIntent = getIntentForResponse(getEditDataFromFields())

    setResult(RESULT_OK, responseIntent)
    finish()
  }

  private fun getEditDataFromFields(): EditData = EditData(
    name = etName.text.toString().trim(),
    surname = etSurname.text.toString().trim(),
    age = etAge.text.toString().trim(),
  )

  data class EditData(
    val name: String,
    val surname: String,
    val age: String,
  ): Serializable

  companion object {
    private const val EXTRA_DATA = "data"

    fun getIntentToStartForResult(context: Context, editData: EditData) =
      Intent(context, FillFormActivity::class.java).apply {
        putExtra(EXTRA_DATA, editData)
      }

    fun getEditDataFromIntent(intent: Intent): EditData? =
      intent.getSerializableExtra(EXTRA_DATA) as EditData?

    private fun getIntentForResponse(editData: EditData) = Intent().apply {
      putExtra(EXTRA_DATA, editData)
    }
  }
}