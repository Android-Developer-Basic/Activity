package otus.gpb.homework.activities

import android.Manifest
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat

class EditProfileActivity : AppCompatActivity() {
    private var imageUri: Uri? = null

    private val tvName: TextView by lazy { findViewById(R.id.textview_name) }
    private val tvSurname: TextView by lazy { findViewById(R.id.textview_surname) }
    private val tvAge: TextView by lazy { findViewById(R.id.textview_age) }

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

    private val requestGalleryContentLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { result: Uri? ->
        result?.let {
            populateImage(result)
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
        findViewById<Button>(R.id.button4).setOnClickListener {
            openFillForm()
        }

        imageView.setOnClickListener {
            showProfileImageChangerDialog()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FILL_FORM && data != null) {
            FillFormActivity.getEditDataFromIntent(data)?.let {
                setEditedDataToViews(it)
            }
        }
    }

    private fun setEditedDataToViews(editData: FillFormActivity.EditData) {
        tvName.text = editData.name
        tvSurname.text = editData.surname
        tvAge.text = editData.age
    }

    private fun getEditDataFromViews(): FillFormActivity.EditData = FillFormActivity.EditData(
        name = tvName.text.toString(),
        surname = tvSurname.text.toString(),
        age = tvAge.text.toString(),
    )

    private fun openFillForm() {
        val data = getEditDataFromViews()
        val intent = FillFormActivity.getIntentToStartForResult(this, data)

        startActivityForResult(intent, REQUEST_CODE_FILL_FORM)
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
        requestImageFromGallery()
    }

    private fun requestImageFromGallery() {
        requestGalleryContentLauncher.launch("image/*")
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))

        imageView.setImageBitmap(bitmap)
        imageUri = uri
    }

    private fun openSenderApp() {
        // В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку
        val appName = "org.telegram.messenger"

        if (isAppAvailable(this, appName)) {
            val intent = createIntentForSenderApp(appName)

            startActivity(Intent.createChooser(intent, "Share with"))
        } else {
            Toast.makeText(this, "Telegram не установлен!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createIntentForSenderApp(appName: String): Intent {
        val text = getTextForSenderApp()

        return Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setPackage(appName)
        }
    }

    private fun getTextForSenderApp(): String {
        return listOf(
            tvName.text.toString(),
            tvSurname.text.toString(),
            tvAge.text.toString(),
        ).joinToString(" ").trim()
    }

    companion object {
        private const val REQUEST_CODE_FILL_FORM = 1
    }
}