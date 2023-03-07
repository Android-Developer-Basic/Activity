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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class EditProfileActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    var savedUri: Uri? = null

    private val fromGalleryContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            populateImage(result)
            savedUri == result
        }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                findViewById<ImageView>(R.id.imageview_photo).apply {
                    setImageDrawable(getDrawable(R.drawable.cat))
                }
            } else {
                val answer =
                    !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                if (answer) {
                    MaterialAlertDialogBuilder(this)
                        .setMessage("открой настройки, чтобы дать разрешение на использование камеры")
                        .setPositiveButton("открыть насторойки") { dialog, id ->
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

    private val setUserContract = registerForActivityResult(MyContract()) { result ->
        findViewById<TextView>(R.id.textview_name).apply { text = result?.name }
        findViewById<TextView>(R.id.textview_surname).apply { text = result?.surname }
        findViewById<TextView>(R.id.textview_age).apply { text = result?.age }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        imageView.setOnClickListener {

            val buttons = arrayOf("сделать фото", "выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setTitle("выбери действие")
                .setItems(buttons) { dialog, which ->
                    val choice = buttons[which]
                    if (choice == "выбрать фото") {
                        fromGalleryContract.launch("image/*")
                    } else {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            MaterialAlertDialogBuilder(this)
                                .setTitle("важно!")
                                .setMessage("для работы камеры нужен досту к ней")
                                .setPositiveButton("дать доступ") { dialog, id ->
                                    permissionCamera.launch(Manifest.permission.CAMERA)
                                }
                                .setNegativeButton("отмена") { dialog, id ->
                                    dialog.cancel()
                                }
                                .show()
                        } else {
                            permissionCamera.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
                .show()
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

        findViewById<Button>(R.id.button4).setOnClickListener {
            setUserContract.launch("")
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri?) {
        val bitmap =
            BitmapFactory.decodeStream(uri?.let { contentResolver.openInputStream(it) })
        imageView.setImageBitmap(bitmap)
        savedUri = uri
    }

    private fun openSenderApp() {
        val name = findViewById<TextView>(R.id.textview_name)
        val surname = findViewById<TextView>(R.id.textview_surname)
        val age = findViewById<TextView>(R.id.textview_age)
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, UserProfile(name.text.toString(), surname.text.toString(), age.text.toString()).toString())
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, savedUri)
            setPackage("org.telegram.messenger")
        }
        startActivity(intent)
    }
}