package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.content.ClipData
import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private var uri: Uri? = null

    private val options: List<Pair<String, () -> Unit>> = listOf(
        "Сделать фото" to ::takePicture,
        "Выбрать фото" to ::getContent,
    )

    /**
     * Установка в качестве аватарки изображения, сделанного на камеру
     */
    private fun takePicture() {
        val granted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        when {
            granted == PermissionChecker.PERMISSION_GRANTED -> {
                populateImage()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                camRationaleDialog()
            }
            else -> {
                launcherUseCamera.launch(Manifest.permission.CAMERA)
            }
        }
    }

    /**
     * Установка в качестве аватарки изображения, выбранного в галерее
     */
    private fun getContent() {
        launcherGetContent.launch("image/*")
    }

    private val launcherTakePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let {
            imageView.setImageBitmap(it)
        } ?: Toast.makeText(this, "Снимок не был сделан", LENGTH_SHORT).show()
    }

    /**
     * Запрос разрешения на использование камеры (при необходимости) и установка полученного изображения в качестве аватарки
     */
    private val launcherUseCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            populateImage()
        } else {
            Toast.makeText(this, "Воспользоваться камерой невозможно", LENGTH_SHORT).show()
        }
    }

    /**
     * Поиск в галерее нужного файла изображения и установка в качестве аватарки
     */
    private val launcherGetContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            populateImage(it)
        } ?: Toast.makeText(this, "Файл не был выбран", LENGTH_SHORT).show()
    }

    /**
     * Редактирование информации о профиле
     */
    private val launcherEditProfile = registerForActivityResult(EditProfileContract()) {
        setUserProfileInfo(it)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("USER", getUserProfileInfo())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            changeAvatarDialog()
        }

        val userProfileInfo = savedInstanceState?.getParcelable<UserProfileInfo>("USER")
        setUserProfileInfo(userProfileInfo)

        val btnEditProfile: Button = findViewById(R.id.btn_edit_profile)
        btnEditProfile.setOnClickListener {
            launcherEditProfile.launch(Unit)
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
    private fun changeAvatarDialog() = AlertDialog.Builder(this)
        .setTitle("Замена аватарки")
        .setItems(options.map { pair -> pair.first }.toTypedArray()) { _, which ->
            options[which].second()
        }
        .create()
        .show()

    private fun camRationaleDialog() = AlertDialog.Builder(this)
        .setTitle("Доступ к камере")
        .setMessage("Тем, у кого в профиле нет аватарки (портрета), люди пишут гораздо реже." +
                "\nХотите сделать фотографию и использовать её в профиле?")
        .setPositiveButton("Дать доступ") { _, _ ->
            launcherUseCamera.launch(Manifest.permission.CAMERA)
        }
        .setNegativeButton("Отмена") { dialog, _ ->
            dialog.cancel()
            openAppSettingsDialog()
        }
        .create()
        .show()

    private fun openAppSettingsDialog() = AlertDialog.Builder(this)
        .setTitle("Переход к настройкам")
        .setPositiveButton("Открыть настройки") { _, _ ->
            openAppSettings()
        }
        .create()
        .show()

    private fun openAppSettings() {
        val settingIntent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(settingIntent)
    }

    private fun openEditProfileDialog() = AlertDialog.Builder(this)
        .setTitle("Переход в редактор профиля")
        .setMessage("Нет данных профиля для отправки.\nПерейти в редактирование профиля?")
        .setPositiveButton("Перейти") { _, _ ->
            launcherEditProfile.launch(Unit)
        }
        .setNegativeButton("Отмена") { dialog, _ ->
            dialog.cancel()
        }
        .create()
        .show()

    private fun populateImage() {
        val resId: Int = R.drawable.cat
        imageView.setImageResource(R.drawable.cat)

        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + resources.getResourcePackageName(resId)
                + "/" + resources.getResourceTypeName(resId)
                + "/" + resources.getResourceEntryName(resId))
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri?) {
        uri?.let {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            imageView.setImageBitmap(bitmap)
            this.uri = uri
        }
    }

    private fun setUserProfileInfo(userProfileInfo: UserProfileInfo?) {
        userProfileInfo?.let {
            findViewById<TextView?>(R.id.textview_name).apply {
                text = context.getString(R.string.label_first_name, it.firstName)
            }
            findViewById<TextView?>(R.id.textview_surname).apply {
                text = context.getString(R.string.label_last_name, it.lastName)
            }
            findViewById<TextView?>(R.id.textview_age).apply {
                text = context.getString(R.string.label_age, it.age)
            }
            populateImage(it.uri)
        }
    }

    private fun getUserProfileInfo(): UserProfileInfo {
        val age = findViewById<TextView?>(R.id.textview_age).text
            .filter { it.isDigit() }
            .toString()
            .toIntOrNull() ?: 0
        return UserProfileInfo(
            firstName = findViewById<TextView?>(R.id.textview_name).text.toString(),
            lastName = findViewById<TextView?>(R.id.textview_surname).text.toString(),
            age = age,
            uri = uri,
        )
    }

    private fun openSenderApp() {
        val messageText: String = arrayOf(
            findViewById<TextView?>(R.id.textview_name).text,
            findViewById<TextView?>(R.id.textview_surname).text,
            findViewById<TextView?>(R.id.textview_age).text,
        ).joinToString("\n")

        if (messageText.isBlank()) {
            openEditProfileDialog()
            return
        }

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain/image/*"
//            data = uri
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//            setPackage("org.telegram.messenger")
            putExtra(Intent.EXTRA_TITLE, "Test title")
            putExtra(Intent.EXTRA_SUBJECT, "Test subject")
            putExtra(Intent.EXTRA_TEXT, messageText)
//            putExtra(Intent.EXTRA_STREAM, uri)
        }
        startActivity(Intent.createChooser(intent, "Share title"))
    }
}
