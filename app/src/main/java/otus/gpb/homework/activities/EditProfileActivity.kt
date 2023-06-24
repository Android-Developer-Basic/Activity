package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView
    private lateinit var buttonEditProfile: Button

    private var permissionRejectedCount = 0
    private var settingsDialogShown = false

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        when {
            isGranted -> {
                imageView.setImageResource(R.drawable.cat)
                permissionRejectedCount = 0
                settingsDialogShown = false
            }
            else -> {
                permissionRejectedCount++
                when {
                    permissionRejectedCount == 1 -> {
                        // первый отказ пропускаем
                    }
                    permissionRejectedCount > 1 && !settingsDialogShown -> {
                        showRationaleDialog()
                    }
                    permissionRejectedCount > 1 && settingsDialogShown -> {
                        showSettingsDialog()
                    }
                }
            }
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    private val fillFormLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {

                val name = data.getStringExtra("name")
                val surname = data.getStringExtra("surname")
                val age = data.getStringExtra("age")


                textViewName.text = name
                textViewSurname.text = surname
                textViewAge.text = age
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)
        buttonEditProfile = findViewById(R.id.button4)

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Выберите действие")
                .setItems(arrayOf("Сделать фото", "Выбрать фото")) { _, which ->
                    when (which) {
                        0 -> handleTakePhoto()
                        1 -> pickImageLauncher.launch("image/*")
                    }
                }
                .show()
        }

        buttonEditProfile.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java).apply {
                putExtra("name", textViewName.text.toString())
                putExtra("surname", textViewSurname.text.toString())
                putExtra("age", textViewAge.text.toString())
            }
            fillFormLauncher.launch(intent)
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

    private fun handleTakePhoto() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(Manifest.permission.CAMERA) -> {
                imageView.setImageResource(R.drawable.cat)
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Приложению требуется разрешение на использование камеры")
            .setPositiveButton("Дать доступ") { _, _ ->
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                settingsDialogShown = true
            }
            .setNegativeButton("Отмена"){ _, _ ->
                settingsDialogShown = true
            }
            .show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Необходимо разрешение на использование камеры")
            .setPositiveButton("Открыть настройки") { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
            }
            .show()
    }

    private fun getUriFromImageView(imageView: ImageView): Uri? {

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        var imageUri: Uri? = null
        try {

            val imagesFolder = File(cacheDir, "images")
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")

            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()

            imageUri = FileProvider.getUriForFile(
                this, BuildConfig.APPLICATION_ID + ".provider", file
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imageUri
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }


    private fun openSenderApp() {
        val imageView: ImageView = findViewById(R.id.imageview_photo)
        val imageUri = getUriFromImageView(imageView)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            putExtra(Intent.EXTRA_TEXT, "${textViewName.text}\n${textViewSurname.text}\n${textViewAge.text}")
            setPackage("org.telegram.messenger")
        }
        startActivity(shareIntent)
    }
}