package otus.gpb.homework.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        when(granted) {
            true -> imageView.setImageResource(R.drawable.cat)
            false -> {}
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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
                            // Здесь позже откроем выбор фотографии
                        }
                    }
                }
                .show()
        }

    }

    private fun clickOnFindPhoto() {
        ActivityResultContracts.TakePicture()
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
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}