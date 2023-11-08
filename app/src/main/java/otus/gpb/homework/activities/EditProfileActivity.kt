package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        when {
            isGranted -> {
                openCameraToMakePhotoForProfileImage()
            }

            !ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA,
            ) -> {
                showOpenAppSettingDialog()
            }

            else -> {
                showRequiredCamaraPermissionDialog()
            }
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
            showProfileImageChangerDialog()
        }
    }

    private fun showProfileImageChangerDialog() {
        AlertDialog.Builder(this)
            .setTitle("Изображение профиля")
            .setMessage("Выберите действие")
            .setPositiveButton("Сделать фото") { _, _ ->
                makePhotoForProfileImage()
            }
            .setNegativeButton("Выбрать фото") { _, _ ->
                choosePhotoForProfileImage()
            }
            .create()
            .show()
    }

    private fun makePhotoForProfileImage() {
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(
            Manifest.permission.CAMERA,
        )
    }

    private fun showRequiredCamaraPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Ты чё пёс!")
            .setMessage("Дай доступ к камере \uD83D\uDC7E")
            .setPositiveButton("Дать доступ") { _, _ ->
                requestCameraPermission()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showOpenAppSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Разрешение на использование камеры")
            .setMessage("В настройках, включите разрешение использовании камеры")
            .setPositiveButton("Открыть настройки") { _, _ ->
                openAppSettings()
            }
            .create()
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts(
                "package",
                packageName,
                null,
            )
        }
        startActivity(intent)
    }

    private fun openCameraToMakePhotoForProfileImage() {
        // на самом деле мы просто вставим изображение котейки 😺
        AppCompatResources.getDrawable(this, R.drawable.cat)?.let {
            imageView.setImageDrawable(it)
        }
    }

    private fun choosePhotoForProfileImage() {

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