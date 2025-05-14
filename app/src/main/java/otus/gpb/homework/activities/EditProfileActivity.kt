package otus.gpb.homework.activities

import android.R.id.message
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
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


const val RESULT_KEY = "result_key"
const val RESULT_FIRSTNAME = "result_firstname"
const val RESULT_SURNAME = "result_surname"
const val RESULT_AGE = "result_age"

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }
            else -> {
                // Пользователь нажал не разрешать
                MaterialAlertDialogBuilder(this)
                    .setMessage("Для показа картинки нужна камера")
                    .setPositiveButton("Дать доступ") { dialog, which ->
                        permissionCamera2.launch(android.Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("Отмена") { dialog, which ->
                    }
                    .show()
            }
        }
    }

    private val permissionCamera2 = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            imageView.setImageResource(R.drawable.cat)
        }
        else {
            MaterialAlertDialogBuilder(this)
                .setPositiveButton("Открыть настройки") { dialog, which ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                    startActivity(intent)
                }
                .show()
        }
    }

    private val takePictures = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { image ->
        imageView.setImageBitmap(image)
    }

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { image ->
        if (image != null) {
            populateImage(image)
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val resultCode = result.resultCode

        if (resultCode == RESULT_OK && data != null) {
            val fname = data.getStringExtra(RESULT_FIRSTNAME)
            val sname = data.getStringExtra(RESULT_SURNAME)
            val age = data.getStringExtra(RESULT_AGE)

            if (fname != null)
                findViewById<TextView>(R.id.textview_name).text = fname
            if (sname != null)
                findViewById<TextView>(R.id.textview_surname).text = sname
            if (age != null)
                findViewById<TextView>(R.id.textview_age).text = age
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Выход без ввода данных", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        val button4 : Button = findViewById(R.id.button4)

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
            MaterialAlertDialogBuilder(this)
                .setNeutralButton("Сделать фото") { dialog, which ->
                    permissionCamera.launch(android.Manifest.permission.CAMERA)
                }
                .setPositiveButton("Выбрать фото") { dialog, which ->
                    takePictureUri.launch("image/*")
                }
                .show()
        }

        button4.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            launcher.launch(intent)
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
        val message = findViewById<TextView>(R.id.textview_name).text.toString() + "\r\n" +
                findViewById<TextView>(R.id.textview_surname).text.toString() + "\r\n" +
                findViewById<TextView>(R.id.textview_age).text.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.setPackage("com.telegram")
        intent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Telegram не установлен", Toast.LENGTH_SHORT).show()
        }
    }
}