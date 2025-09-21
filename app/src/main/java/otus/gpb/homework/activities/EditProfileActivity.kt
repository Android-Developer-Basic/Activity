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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import otus.gpb.homework.alertsdialogs.EditProfileDialog
import otus.gpb.homework.alertsdialogs.EditProfileListener
import otus.gpb.homework.alertsdialogs.PermissionDialogListener
import otus.gpb.homework.alertsdialogs.PermissionsAlertDialog

open class EditProfileActivity : AppCompatActivity(), EditProfileListener,
    PermissionDialogListener {

    private lateinit var imageView: ImageView
    private lateinit var textviewName: TextView
    private lateinit var textviewSurname: TextView
    private lateinit var textviewAge: TextView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                takePhoto()
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                populateImage(uri)
            }
        }

    private val getFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val code = result.resultCode

            when (code) {
                RESULT_OK -> {
                    fillTextFields(data)
                }

                RESULT_CANCELED -> {}
                else -> {}
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textviewName = findViewById<TextView>(R.id.textview_name)
        textviewSurname = findViewById<TextView>(R.id.textview_surname)
        textviewAge = findViewById<TextView>(R.id.textview_age)

        val btn4 = findViewById<Button>(R.id.button4)

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
            EditProfileDialog(this).show(supportFragmentManager, "CHOOSING")
        }

        btn4.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            getFormLauncher.launch(intent)
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
//        TODO(
//            "В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. " +
//                    "В качестве extras передайте заполненные строки и картинку"
//        )

        val name = textviewName.text?.toString() ?: ""
        val surname = textviewSurname.text?.toString() ?: ""
        val age = textviewAge.text?.toString() ?: ""
        val imageUri = imageView.id

        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            setPackage("org.telegram.messenger")
            putExtra("name", name)
            putExtra("surname", surname)
            putExtra("age", age)
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Telegram не найден: $e", Toast.LENGTH_SHORT).show()
        }

    }

    private fun requestCameraPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun showPermissionsRational() {
        PermissionsAlertDialog(this).show(supportFragmentManager, "PERMISSIONS")
    }

    private fun takePhoto() {
        imageView.setImageResource(R.drawable.cat)
    }

    override fun callCameraPermissionLauncher() {
        when {
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) -> {
                takePhoto()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionsRational()
            }

            else -> {
                requestCameraPermissions()
            }
        }
    }

    override fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    override fun getCameraPermissionLauncher() {
        when {
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) -> {
                takePhoto()
            }

            else -> {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
            }
        }
    }

    private fun fillTextFields(intent: Intent?) {
        val bundle = intent?.getBundleExtra("BundleTitle")
        val name = bundle?.getString("name")
        val surname = bundle?.getString("surname")
        val age = bundle?.getString("age")

        textviewName.text = name
        textviewSurname.text = surname
        textviewAge.text = age
    }
}
