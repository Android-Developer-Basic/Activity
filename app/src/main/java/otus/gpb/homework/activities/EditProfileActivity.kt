package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button4: Button
    private lateinit var textview_name: TextView
    private lateinit var textview_surname: TextView
    private lateinit var textview_age: TextView
    private  var imageUri: Uri? = null

    private val launcherFillFormActivity = registerForActivityResult(
        ContractFillForm()) { result ->
        if (result != null) {
            textview_name.text = (result as Person).firstName
            textview_surname.text = (result as Person).lastName
            textview_age.text = (result as Person).age.toString()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.start)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.imageview_photo)
        textview_name = findViewById(R.id.textview_name)
        textview_surname = findViewById(R.id.textview_surname)
        textview_age = findViewById(R.id.textview_age)
        button4 = findViewById(R.id.button4)

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


        button4.setOnClickListener {
                val person  = Person(
                    textview_name.text?.toString()?:"",
                    textview_surname.text?.toString()?:"",
                    textview_age.text?.toString()?.let {
                        if (it.matches("\\d+".toRegex()) == true) {
                            it.toInt()
                        } else 0
                    }?:0
                    )
                launcherFillFormActivity.launch(person)

            }


        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.alert_dialog_title))
                .setMessage(resources.getString(R.string.alert_dialog_supporting_text))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which -> }
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    permissionCameraLauncher()
                }
                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                    takePictureUri.launch("image/*")
                }
                .show()
        }
    }

    private fun permissionCameraLauncher() =
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                takePictures.launch(null)
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                showRationaleDialog()
            }

            else -> {
                requestPermissionAndAction.launch(android.Manifest.permission.CAMERA)
            }
        }


    private val requestPermissionAndAction: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            granted ->
                when {
                    granted -> takePictures.launch(null)
                    else -> {
                    }
                }
        }


    private val takePictures = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()) {
        image -> imageView.setImageBitmap(image)
    }


    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        uri -> uri?.let {
            populateImage(it)
            imageUri = uri
        }
    }


    private fun openAppPermissionSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }


    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Нужен доступ к камере")
            .setMessage("Чтобы сделать фото, разрешите доступ в настройках")
            .setPositiveButton("Открыть настройки") { _, _ ->
                openAppPermissionSettings()
            }
            .setNegativeButton("Отмена", null)
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
        if (imageUri != null) {
            val intent = Intent().apply {
                setPackage("org.telegram.messenger")
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${textview_name.text} ${textview_surname.text} ${textview_age.text}"
                )
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            runCatching { startActivity(intent) }
                .onFailure {
                    Toast.makeText(
                        this,
                        "Не удалось отправить сообщение!",
                        Toast.LENGTH_SHORT).show()
                }
        }

        Toast.makeText(this, "Только для фото из галереи", Toast.LENGTH_SHORT).show()
    }
}