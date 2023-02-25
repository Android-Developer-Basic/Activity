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

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var userName: TextView
    private lateinit var userSurname: TextView
    private lateinit var userAge: TextView
    private var imageUri: Uri? = null

    private var userDTO = UserDTO(null, null, null)

    private val contractGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                populateImage(result)
                imageUri = result
            }
        }

    private val contractFillForm = registerForActivityResult(FillFormContract()) { result ->
        if (result != null) {
            userName.text = result.name
            userSurname.text = result.surname
            userAge.text = result.age

            userDTO = UserDTO(
                userName.text.toString(),
                userSurname.text.toString(),
                userAge.text.toString()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        userName = findViewById(R.id.textview_name)
        userSurname = findViewById(R.id.textview_surname)
        userAge = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Фотография")
                .setMessage("Необходимо выбрать источник изображения")
                .setNegativeButton("Сделать фото") { _, _ ->
                    getPhotoFromCamera()
                }
                .setPositiveButton("Выбрать фото") { _, _ ->
                    contractGallery.launch("image/*")
                }
                .show()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            contractFillForm.launch(userDTO)
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

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val intent = Intent(Intent.ACTION_SEND)
            .apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    "ИМЯ: ${userDTO.name}\nФАМИЛИЯ: ${userDTO.surname}\nВОЗРАСТ: ${userDTO.age}"
                )
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                setPackage("org.telegram.messenger")
            }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Приложение Telegram не установлено на ваш телефон!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.cat}")
    }
}