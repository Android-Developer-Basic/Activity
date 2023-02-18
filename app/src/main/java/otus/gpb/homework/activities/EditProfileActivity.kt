package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Фотография")
                .setMessage("Необходимо выбрать источник изображения")
                .setNegativeButton("Сделать фото") { _, _ ->
                    getPhotoFromCamera()
                }
                .setPositiveButton("Выбрать фото") { _, _ -> }
                .show()
        }

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


    // всё что ниже хотел в отдельный класс

    private fun getPhotoFromCamera() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            rationaleDialog()
        } else {
            permissionCamera.launch(Manifest.permission.CAMERA)
        }
    }

    private fun rationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("ВАЖНО")
            .setMessage("Для дальнейшего функционирования приложение необходимо предаставить разрешение. Это безопасно и ваши данные не будут предоставлены третей стороне.")
            .setPositiveButton("Дать доступ") { _, _ ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
    }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    setPhoto()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    openSettings()
                }
            }
        }

    private fun openSettings() {
        MaterialAlertDialogBuilder(this)
            .setPositiveButton("Открыть настройки") { _, _ ->
                val uri = Uri.fromParts("package", packageName, null)
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
                startActivity(intent)
            }
            .show()
    }

    private fun setPhoto() {
        findViewById<ImageView>(R.id.imageview_photo).setImageDrawable(
            AppCompatResources.getDrawable(applicationContext, R.drawable.cat)
        )
    }
}


//Toast.makeText(this, "СРАБОТАЛО", Toast.LENGTH_LONG).show()