package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
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

class EditProfileActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textviewName: TextView
    private lateinit var textviewSurname: TextView
    private lateinit var textviewAge: TextView
    private lateinit var buttonEditProfile: Button
    private var requestPermissionCount = 0
    private var uriForTelegram: Uri? = null
    private var contentForTelegram: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        textviewName = findViewById(R.id.textview_name)
        textviewSurname = findViewById(R.id.textview_surname)
        textviewAge = findViewById(R.id.textview_age)
        buttonEditProfile = findViewById(R.id.button4)

        imageView.setOnClickListener {
            showChangeAvatarDialog()
        }

        buttonEditProfile.setOnClickListener {
            fillFromActivity.launch("Open Edit Account")
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
        uriForTelegram = uri
    }

    private fun openSenderApp() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "image/*"
        sendIntent.putExtra(Intent.EXTRA_STREAM, uriForTelegram)
        sendIntent.putExtra(Intent.EXTRA_TEXT, contentForTelegram)
        sendIntent.setPackage("org.telegram.messenger")
        startActivity(sendIntent)
    }
    private fun showChangeAvatarDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Изменить аватарку")
            setPositiveButton("Сделать фото") { _, _ ->
                if (requestPermissionCount == 0) {
                    permissionCamera.launch(Manifest.permission.CAMERA)
                    requestPermissionCount++
                } else {
                    checkPermission()
                }
            }
            setNegativeButton("Выбрать фото") { _, _ ->
                chooseImage.launch("image/*")
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private val chooseImage = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        if (result != null) {
            populateImage(result)
        }
    }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    imageView.setImageResource(R.drawable.cat)
                }

                else -> {}
            }
        }

    private val fillFromActivity = registerForActivityResult(Contract()) { result ->
        val name = result?.getStringExtra("name")
        val surname = result?.getStringExtra("surname")
        val age = result?.getStringExtra("age")

        textviewName.text = name
        textviewSurname.text = surname
        textviewAge.text = age
        contentForTelegram = "Мое имя $name, моя фамилия $surname и мне $age лет"
    }

    private fun checkPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            showExplanationDialog()
        } else {
            openSettingsDialog()
        }
    }

    private fun showExplanationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Предоставьте доступ к камере")
            setMessage("Доступ к камере необходим для того, чтобы вы могли делать фотографии из приложения")
            setPositiveButton("Дать доступ") { _, _ ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            setNegativeButton("Отмена", null)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun openSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Открыть настройки")
            setPositiveButton("Дать доступ") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}