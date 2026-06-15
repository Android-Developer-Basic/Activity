package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var surnameTextView: TextView
    private lateinit var ageTextView: TextView
    private var selectedPhotoUri: Uri? = null

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imageView.setImageResource(R.drawable.cat)
        } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            showSettingsDialog()
        }
    }

    private val pickPhotoLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedPhotoUri = uri
            populateImage(uri)
        }
    }

    private val fillFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            nameTextView.text = data.getStringExtra(FillFormActivity.EXTRA_NAME).orEmpty()
            surnameTextView.text = data.getStringExtra(FillFormActivity.EXTRA_SURNAME).orEmpty()
            ageTextView.text = data.getStringExtra(FillFormActivity.EXTRA_AGE).orEmpty()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        nameTextView = findViewById(R.id.textview_name)
        surnameTextView = findViewById(R.id.textview_surname)
        ageTextView = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            showPhotoActionDialog()
        }

        findViewById<Button>(R.id.button_edit_profile).setOnClickListener {
            fillFormLauncher.launch(Intent(this, FillFormActivity::class.java))
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

    private fun showPhotoActionDialog() {
        val actions = arrayOf("Сделать фото", "Выбрать фото")
        MaterialAlertDialogBuilder(this)
            .setItems(actions) { _, which ->
                when (which) {
                    TAKE_PHOTO_POSITION -> requestCameraPermission()
                    PICK_PHOTO_POSITION -> pickPhotoLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            }
            .show()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                imageView.setImageResource(R.drawable.cat)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showCameraRationaleDialog()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showCameraRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Нужен доступ к камере")
            .setMessage("Камера нужна, чтобы сделать фото для профиля.")
            .setPositiveButton("Дать доступ") { _, _ ->
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Доступ к камере запрещён")
            .setMessage("Разрешение можно включить в настройках приложения.")
            .setPositiveButton("Открыть настройки") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
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
        val profileText = listOf(
            nameTextView.text,
            surnameTextView.text,
            ageTextView.text
        ).joinToString(separator = "\n")

        val intent = Intent(Intent.ACTION_SEND).apply {
            setPackage(TELEGRAM_PACKAGE)
            type = selectedPhotoUri?.let { "image/*" } ?: "text/plain"
            putExtra(Intent.EXTRA_TEXT, profileText)
            selectedPhotoUri?.let { uri ->
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                grantUriPermission(TELEGRAM_PACKAGE, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }

        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            // Telegram is not installed.
        }
    }

    private companion object {
        const val TAKE_PHOTO_POSITION = 0
        const val PICK_PHOTO_POSITION = 1
        const val TELEGRAM_PACKAGE = "org.telegram.messenger"
    }
}
