package otus.gpb.homework.activities

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
import android.widget.Toast.LENGTH_LONG
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editButton: Button
    private lateinit var nameText: TextView
    private lateinit var secondNameText: TextView
    private lateinit var ageText: TextView

    private var imageUri: Uri? = null
    private var userDTO: UserDTO? = null

    private val resultCameraContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                imageUri = null
                imageView.setImageDrawable(
                    AppCompatResources.getDrawable(this, R.drawable.cat)
                )
            } else {
                if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Разрешение камеры")
                        .setMessage("Чтобы продолжить, дай разрешение использовать камеру")
                        .setPositiveButton("Открыть настройки") { _, _ ->
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    val uri = Uri.fromParts("package", packageName, null)
                                    data = uri
                                }
                            startActivity(intent)
                        }
                        .show()
                }
            }
        }
    private val resultGalleryContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) populateImage(uri)
        }
    private val resultFillFormContract =
        registerForActivityResult(ContractFillFormActivity()) {
            if (it == null) return@registerForActivityResult
            userDTO = it
            nameText.text = it.name
            secondNameText.text = it.surname
            ageText.text = it.age.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editButton = findViewById(R.id.button)
        nameText = findViewById(R.id.textview_name)
        secondNameText = findViewById(R.id.textview_surname)
        ageText = findViewById(R.id.textview_age)
        imageView.setOnClickListener { imageViewOnClick() }
        editButton.setOnClickListener { resultFillFormContract.launch(userDTO) }

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

    private fun imageViewOnClick() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Фото")
            .setMessage("Выбери или сделай фото для профиля")
            .setNegativeButton("Сделать фото") { _, _ ->
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Разрешение камеры")
                        .setMessage("Разрешение требуется, чтобы сделать фото для профиля")
                        .setNegativeButton("Отмена") { _, _ ->
                            // Do nothing
                        }
                        .setPositiveButton("ОК") { _, _ ->
                            resultCameraContract.launch(android.Manifest.permission.CAMERA)
                        }
                        .show()
                } else {
                    resultCameraContract.launch(android.Manifest.permission.CAMERA)
                }
            }
            .setPositiveButton("Выбери фото") { _, _ ->
                resultGalleryContract.launch("image/*")
            }
            .show()
    }


    private fun populateImage(uri: Uri) {
        imageUri = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        if (userDTO == null) {
            Toast.makeText(
                this, "Сначала заполни профиль", LENGTH_LONG
            ).show()
            return
        }

        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                type = "image/*"
                if (imageUri != null) {
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Имя: ${userDTO?.name}\n" +
                            "Фамилия: ${userDTO?.surname}\n" +
                            "Возраст: ${userDTO?.age}\n"
                )
            }
            startActivity(Intent.createChooser(intent, "Поделиться"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, "Telegram не установлен", LENGTH_LONG
            ).show()
        }
    }
}
