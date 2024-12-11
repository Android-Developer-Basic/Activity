package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var cameraAccessTime = 0

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        val name = result.data?.extras?.getString("name", "Default")
        val surname = result.data?.extras?.getString("surname", "Default")
        val age = result.data?.extras?.getString("age", "Default")

        findViewById<TextView>(R.id.textview_name).text = name
        findViewById<TextView>(R.id.textview_surname).text = surname
        findViewById<TextView>(R.id.textview_age).text = age
    }

    val takeImageUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    val permissionCameraStatus = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }

            else -> {

                when (cameraAccessTime) {
                    0 -> ++cameraAccessTime
                    1 -> {
                        ++cameraAccessTime
                        showRationaleDialog()
                    }

                    else -> if (cameraAccessTime >= 2) {
                        showOpenSettingsDialog()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        findViewById<Button>(R.id.buttonEditProfile).setOnClickListener {
            openFillFormActivity()
        }
        imageView.setOnClickListener {
            showAlertDialog()
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

    private fun showAlertDialog() {
        val choosingActions = arrayOf("Сделать фото", "Выбрать фото")
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Take a picture")
            setSingleChoiceItems(choosingActions, -1) { dialog, item ->
                when (item) {
                    0 -> permissionCameraStatus.launch(
                        Manifest.permission.CAMERA
                    )

                    1 -> takeImageUri.launch(input = "image/*")
                }
            }.show()
        }
    }

    private fun showRationaleDialog() {

        MaterialAlertDialogBuilder(this).apply {
            setMessage("The camera should take a picture")
            setPositiveButton("Дать доступ") { dialog, which ->
                permissionCameraStatus.launch(
                    Manifest.permission.CAMERA
                )
            }
            setNegativeButton("Отмена") { dialog, which -> return@setNegativeButton }.show()
        }
    }

    private fun showOpenSettingsDialog() {

        MaterialAlertDialogBuilder(this).apply {
            setPositiveButton("Открыть настройки") { dialog, which ->
                openAppSettings()
            }.show()
        }
    }

    fun openAppSettings() {
        val dialogIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }

    fun openFillFormActivity() {
        launcher.launch(Intent(this, FillFormActivity::class.java))
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.tag = uri
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val textviewName = findViewById<TextView>(R.id.textview_name)
        val textviewSurname = findViewById<TextView>(R.id.textview_surname)
        val textviewAge = findViewById<TextView>(R.id.textview_age)
        val uriToImage = imageView.tag.toString()

        val telegramIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            `package` = "org.telegram.messenger"
            putExtra(Intent.EXTRA_STREAM, uriToImage)
            putExtra(
                Intent.EXTRA_TEXT,
                "Name: ${textviewName.text} Surname: ${textviewSurname.text} Age: ${textviewAge.text}"
            )
        }
        startActivity(telegramIntent)
    }
}