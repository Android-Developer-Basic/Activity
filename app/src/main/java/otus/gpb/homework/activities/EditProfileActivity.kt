package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView
    private lateinit var buttonEditProfile: Button
    private var imageUri: Uri = Uri.EMPTY
    private var showFirst: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)
        buttonEditProfile = findViewById(R.id.button_edit_profile)

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

        // В классе EditProfileActivity по клику на ImageView с id=imageview_photo покажите Alert Dialog с выбором действия.
        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.caption))
                .setMessage(resources.getString(R.string.select_action))
                .setPositiveButton(resources.getString(R.string.photo_new)) { _, _ ->
                    // По клику на кнопку Сделать фото запросите у пользователя Runtime Permission на использование камеры.
                    // Обработайте следующие возможные сценарии:
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager. PERMISSION_GRANTED) {
                        // Пользователь не разрешил использовать камеру первый раз → ничего не делаем
                        if (showFirst == 0) {
                            getPhoto.launch(Manifest.permission.CAMERA)
                        }
                        if (showFirst == 1) {
                            // Пользователь еще раз запросил разрешение на использование камеры после отмены →
                            // покажите Rationale Dialog, и объясните зачем вам камера
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.camera_access_caption))
                                .setMessage(resources.getString(R.string.camera_access_description))
                                .setPositiveButton(resources.getString(R.string.camera_access_true)) { _, _ ->
                                    getPhoto.launch(Manifest.permission.CAMERA)
                                }
                                .setNeutralButton(resources.getString(R.string.camera_access_false)) { _, _ ->
                                }
                                .show()
                        }
                        if (showFirst > 1) {
                            // Пользователь повторно запретил использовать камеру → Покажите диалоговое окно с одной кнопкой → “Открыть настройки”.
                            // По клику на кнопку отправьте пользователя в настройки приложения, с возможностью поменять разрешение
                            MaterialAlertDialogBuilder(this)
                                .setTitle(resources.getString(R.string.camera_access_caption))
                                .setMessage(resources.getString(R.string.camera_access_description))
                                .setPositiveButton(resources.getString(R.string.access_settings)) { _, _ ->
                                    getPhoto.launch(Manifest.permission.CAMERA)
                                }
                                .show()
                        }
                        showFirst ++
                    } else {
                        //Пользователя выдал разрешение на использование камеры → отобразите в ImageView ресурс R.drawable.cat
                        imageView.setImageResource(R.drawable.cat)
                    }
                }
                .setNeutralButton(resources.getString(R.string.photo_choose)) { _, _ ->
                    takePictureUri.launch("image/*")
                }
                .show()
        }
        buttonEditProfile.setOnClickListener {
            editProfileLauncher.launch(Intent(this, FillFormActivity::class.java))
        }
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
        val intentForTG = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            setPackage("org.telegram.messenger")
            action = Intent.ACTION_SEND
            if (!Uri.EMPTY.equals(imageUri)) {
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            putExtra(
                Intent.EXTRA_TEXT,
                "${textViewName.text}\n${textViewSurname.text}\n${textViewAge.text}"
            )
        }
        startActivity(intentForTG)    }

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    private val getPhoto =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                // Пользователя выдал разрешение на использование камеры ...
                granted -> {
                    // ... отобразите в ImageView ресурс R.drawable.cat
                    imageView.setImageResource(R.drawable.cat)
                }
                else -> {
                    // откроем окно настроек
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                    startActivity(intent)
                }
            }
        }

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val resultCode = result.resultCode
        if (resultCode == RESULT_OK && data != null) {
            textViewName.text = data.getStringExtra(KEY_NAME)
            textViewSurname.text = data.getStringExtra(KEY_SURNAME)
            textViewAge.text = data.getStringExtra(KEY_AGE)

        }
    }

}
