package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

const val KEY_NAME = "name"
const val KEY_SURNAME = "surname"
const val KEY_AGE = "age"

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textSurname: TextView
    private lateinit var textAge: TextView

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        textName = findViewById(R.id.textview_name)
        textSurname = findViewById(R.id.textview_surname)
        textAge = findViewById(R.id.textview_age)

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
            MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.dialogTitle))
                .setItems(resources.getTextArray(R.array.dialogActions)) { _, item ->
                    when (item) {
                        0 -> makePhoto()
                        1 -> getPhoto()
                    }
                }.show()
        }

        findViewById<Button>(R.id.button_form).apply {
            setOnClickListener {
                openFillForm()
            }
        }
    }

    private fun openFillForm() {
        launchFillForm.launch(Intent(this, FillFormActivity::class.java).apply {
            putExtra(KEY_NAME, textName.text)
            putExtra(KEY_SURNAME, textSurname.text)
            putExtra(KEY_AGE, textAge.text)
        })
    }

    private fun gotoSettings() = MaterialAlertDialogBuilder(this).setNeutralButton("Открыть настройки") { _, _ ->
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)))
    }.show()

    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
                currentImageUri = null
            }

            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> gotoSettings() // если "больше не спрашивать"
        }
    }

    private fun makePhoto() {
        // повторный запрос на использование камеры
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            MaterialAlertDialogBuilder(this).setMessage("Очень нужен доступ к камере!").setNegativeButton("Отмена", null)
                .setPositiveButton("Дать доступ") { _, _ ->
                    permission.launch(Manifest.permission.CAMERA)
                }.show()
        } else permission.launch(Manifest.permission.CAMERA)
    }

    private val gallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        currentImageUri = it
        if (it != null) populateImage(it)
    }

    private fun getPhoto() {
        gallery.launch("image/*")
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }


    private val launchFillForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            textName.text = it.data?.getStringExtra(KEY_NAME)
            textSurname.text = it.data?.getStringExtra(KEY_SURNAME)
            textAge.text = it.data?.getStringExtra(KEY_AGE)
        }
    }

    private fun openSenderApp() {
        val telega = "org.telegram.messenger"
        val intent: Intent = Intent(Intent.ACTION_SEND).apply {
            `package` = telega
            type = "image/*"
            putExtra(Intent.EXTRA_TEXT, "${textName.text}\n${textSurname.text}\n${textAge.text}")
            if (currentImageUri != null)
                putExtra(Intent.EXTRA_STREAM, currentImageUri)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Нет Телеграмма!", Toast.LENGTH_SHORT).show()
        }
    }
}