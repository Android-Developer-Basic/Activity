package otus.gpb.homework.activities

import android.Manifest
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

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView

    private var uri: Uri? = null

    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        this.uri = it
        it?.let(::populateImage)
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> imageView.setImageResource(R.drawable.cat)
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                MaterialAlertDialogBuilder(this)
                    .setMessage("Доступ к камере нужен, чтобы делать фото")
                    .setPositiveButton("Дать доступ") { _, _ ->
                        requestPermissionCamera()
                    }
                    .setNegativeButton("Отмена") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
        }
    }

    private val fillFormContract = registerForActivityResult(FillFormActivity.Contract()) {
        it?.let {
            textViewName.text = it["name"]
            textViewSurname.text = it["surname"]
            textViewAge.text = it["age"]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            showPhotoDialog()
        }

        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)

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

        findViewById<Button>(R.id.button4).setOnClickListener {
            fillFormContract.launch(mapOf(
                Pair("name", textViewName.text.toString()),
                Pair("surname", textViewSurname.text.toString()),
                Pair("age", textViewAge.text.toString())
            ))
        }
    }

    private fun showPhotoDialog() {
        MaterialAlertDialogBuilder(this)
            .setItems(
                arrayOf(
                    "Сделать фото",
                    "Выбрать фото"
                )
            ) { _, index ->
                when (index) {
                    0 -> requestPermissionCamera()
                    1 -> galleryContract.launch("image/*")
                }
            }
            .show()
    }

    private fun requestPermissionCamera() {
        permissionCamera.launch(Manifest.permission.CAMERA)
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage("org.telegram.messenger")
        intent.putExtra(Intent.EXTRA_TEXT, """
            ${textViewName.text}
            ${textViewSurname.text}
            ${textViewAge.text}
        """.trimIndent())
        intent.putExtra(Intent.EXTRA_STREAM , uri);
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        } ?: Toast.makeText(this, "Telegram не установлен", Toast.LENGTH_SHORT).show()
    }
}