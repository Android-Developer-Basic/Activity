package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
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

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var cameraRejections = 0
    private var profileData = ProfileData("John", "Smith", "100")
    private var imageUri: Uri? = null

    private val profileDataContract = registerForActivityResult(ProfileDataContract()) { result ->
        result?.apply {
            profileData = this
            fillProfileTextFields()
        }
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }
            else -> {
                cameraRejections
                    .takeIf { it > 0 }
                    ?.let {
                        AlertDialog.Builder(this)
                            .setMessage("Дайте доступ к камере в настройках приложения")
                            .setPositiveButton("Открыть настройки") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", packageName, null)
                                }
                                startActivity(intent)
                            }
                            .create().show()
                    }
                    ?: cameraRejections++
            }
        }
    }
    private val pickPhoto = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.apply {
            populateImage(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        fillProfileTextFields()
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
            AlertDialog.Builder(this)
                .setTitle("Сфотографировать или выбрать из галереи?")
                .setItems(arrayOf("Сделать фото", "Выбрать фото")) { _, which ->
                    when (which) {
                        0 -> cameraRejections
                            .takeIf {
                                ContextCompat.checkSelfPermission(
                                    this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                        && it > 0
                            }
                            ?.let {
                                AlertDialog.Builder(this)
                                    .setTitle("Нужна камера")
                                    .setMessage("Без камеры не сфотографировать")
                                    .setPositiveButton("Дать доступ") { _, _ ->
                                        permissionCamera.launch(Manifest.permission.CAMERA)
                                    }.setNegativeButton("Отмена") { _, _ -> }
                                    .create().show()
                            }
                            ?: permissionCamera.launch(Manifest.permission.CAMERA)
                        1 -> pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }.create().show()
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            profileDataContract.launch(profileData)
        }
    }

    private fun fillProfileTextFields() {
        findViewById<TextView>(R.id.textview_name).text = profileData.firstName
        findViewById<TextView>(R.id.textview_surname).text = profileData.lastName
        findViewById<TextView>(R.id.textview_age).text = profileData.age
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        imageUri = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val intent = Intent(Intent.ACTION_SEND)
            .setType("*/*")
            .addCategory(Intent.CATEGORY_DEFAULT)
            // package name of APK downloaded from https://telegram.org/android
            .setPackage("org.telegram.messenger.web")
        imageUri?.apply { intent.putExtra(Intent.EXTRA_STREAM, this) }
        profileData.apply { intent.putExtra(Intent.EXTRA_TEXT, "$firstName\n$lastName\n$age") }
        startActivity(intent)
    }
}