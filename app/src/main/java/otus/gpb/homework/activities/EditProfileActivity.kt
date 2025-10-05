package otus.gpb.homework.activities

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.TextView
import android.content.Intent
import android.widget.Button

class EditProfileActivity : AppCompatActivity() {

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGanted ->
            if (isGanted) {
                imageView.setImageResource(R.drawable.cat)
            } else {
                showCameraRationaleDialog()
            }
        }
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                populateImage(it)
            }
        }

    private val fillFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                findViewById<TextView>(R.id.textview_name).text = data?.getStringExtra("name")
                findViewById<TextView>(R.id.textview_surname).text = data?.getStringExtra("surname")
                findViewById<TextView>(R.id.textview_age).text = data?.getStringExtra("age")
            }
        }


    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            val items = arrayOf("Сделать фото", "Выбрать фото")
            MaterialAlertDialogBuilder(this).setTitle("Выберите действие")
                .setItems(items) { dialog, which ->
                    when (which) {
                        0 -> requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        1 -> pickImageLauncher.launch("image/*")
                    }
                }.show()
            findViewById<Button>(R.id.button4).setOnClickListener {
                val intent = Intent(this, FillFormActivity::class.java)
                fillFormLauncher.launch(intent)
            }
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

    private fun showCameraRationaleDialog() {
        MaterialAlertDialogBuilder(this).setTitle("Доступ к камере")
            .setMessage("Чтобы сделать фото, разрешите использование")
            .setPositiveButton("Дать доступ") { dialog, _ ->
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                dialog.dismiss()
            }.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val name = findViewById<TextView>(R.id.textview_name).text.toString()
        val surname = findViewById<TextView>(R.id.textview_surname).text.toString()
        val age = findViewById<TextView>(R.id.textview_age).text.toString()

        val profileText = "Имя: $name\nФамилия: $surname\nВозраст: $age"
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_TEXT, profileText)
            selectedImageUri?.let {
                putExtra(Intent.EXTRA_STREAM, it)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            setPackage("org.telegram.messenger")
        }
        val telegramApp = packageManager.resolveActivity(sendIntent, 0)
        if (telegramApp != null) {
            startActivity(sendIntent)
        } else {
            val chooser = Intent.createChooser(sendIntent, "Отправить профиль через:")
            startActivity(chooser)
        }
    }
}