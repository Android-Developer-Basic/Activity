package otus.gpb.homework.activities

import android.content.ClipData
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var editView: TextView

    private lateinit var nameInput: TextView
    private lateinit var surnameInput: TextView
    private lateinit var ageInput: TextView
    private var selectedImageUri: Uri? = null

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        when(granted) {
            true -> imageView.setImageResource(R.drawable.cat)
            false -> {}
        }

    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            populateImage(uri)
        }
    }

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data

            findViewById<TextView>(R.id.textview_name).text =
                data?.getStringExtra("name")

            findViewById<TextView>(R.id.textview_surname).text =
                data?.getStringExtra("surname")

            findViewById<TextView>(R.id.textview_age).text =
                data?.getStringExtra("age")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editView = findViewById(R.id.button4)
        nameInput = findViewById(R.id.textview_name)
        surnameInput = findViewById(R.id.textview_surname)
        ageInput = findViewById(R.id.textview_age)

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setItems(arrayOf("Сделать фото", "Выбрать фото")) { _, which ->
                    when (which) {
                        0 -> {
                            clickOnTakePhoto()
                        }

                        1 -> {
                            pickImageLauncher.launch("image/*")
                        }
                    }
                }
                .show()
        }

        editView.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java).apply {
                putExtra("name", nameInput.text.toString())
                putExtra("surname", surnameInput.text.toString())
                putExtra("age", ageInput.text.toString())
            }
            editProfileLauncher.launch(intent)
        }

    }

    private fun clickOnTakePhoto() {
        val permission = android.Manifest.permission.CAMERA

        if (shouldShowRequestPermissionRationale(permission)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Доступ к камере")
                .setMessage("Доступ к камере нужен, чтобы сделать фото профиля.")
                .setPositiveButton("Дать доступ") { _, _ ->
                    cameraPermissionLauncher.launch(permission)
                }
                .setNegativeButton("Отмена") { _, _ ->
                    openSetting()
                }
                .show()
        } else {
            cameraPermissionLauncher.launch(permission)

        }
    }

    private fun openSetting() {
       MaterialAlertDialogBuilder(this).setTitle("Доступ к камере")
            .setNeutralButton("Открыть настройки") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    "package:$packageName".toUri()
                )
                startActivity(intent)
            }
            .show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val uri = selectedImageUri ?: return

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            setPackage("org.telegram.messenger")

            putExtra(
                Intent.EXTRA_TEXT,
                buildString {
                    appendLine("Имя: ${nameInput.text}")
                    appendLine("Фамилия: ${surnameInput.text}")
                    append("Возраст: ${ageInput.text}")
                }
            )
            putExtra(Intent.EXTRA_STREAM, uri)

            clipData = ClipData.newUri(contentResolver, "Фото", uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val component = intent.resolveActivity(packageManager)

        if (component == null) {
            Toast.makeText(
                this,
                "Не найдено приложение для отправки",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        intent.component = component
        startActivity(intent)
    }


}