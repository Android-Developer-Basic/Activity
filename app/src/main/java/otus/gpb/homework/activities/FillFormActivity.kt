package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : Activity() {
    val binding by lazy { ActivityFillFormBinding.inflate(layoutInflater) }
    val userData = PersonData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userData.name = intent.getStringExtra("name")
        userData.surName = intent.getStringExtra("surName")
        userData.age = intent.getIntExtra("age", -1)

        binding.editTextName.apply {
            setText(userData.name)
            setOnEditorActionListener { _, _, _ ->
                userData.name = text.toString()
                true
            }
        }

        binding.editTextSurname.apply {
            setText(userData.surName)
            setOnEditorActionListener { _, _, _ ->
                userData.surName = text.toString()
                true
            }
        }

        binding.editTextAge.apply {
            setText(userData.age.toString())
            setOnEditorActionListener { _, _, _ ->
                userData.age = text.toString().toInt()
                true
            }
        }

        binding.buttonApply.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("name", userData.name)
                putExtra("surName", userData.surName)
                putExtra("age", userData.age)
            })
            finish()
        }
    }
}