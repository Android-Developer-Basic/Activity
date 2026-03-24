package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editProfile: Button
    private lateinit var userFirstName: TextView
    private lateinit var userSurName: TextView
    private lateinit var userBirthday: TextView

    private var pictureUri: Uri? = null

    companion object {
        private const val LOG_TAG = "VPM_Log"
        private const val TELEGRAM_PACKAGE = "org.telegram.messenger"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initViews()
        setupToolbar()
        setupListeners()
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageview_photo)
        editProfile = findViewById(R.id.Edit_profile_button)
        userFirstName = findViewById(R.id.textview_name)
        userSurName = findViewById(R.id.textview_surname)
        userBirthday = findViewById(R.id.textview_age)
    }

    private fun setupToolbar() {
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

    private fun setupListeners() {
        imageView.setOnClickListener { showImageSourceDialog() }

        editProfile.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            fillFormAct.launch(intent)
        }
    }

    private fun showImageSourceDialog() {
        val items = arrayOf("Сделать фото", "Выбрать фото")

        MaterialAlertDialogBuilder(this)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> setCameraPermission.launch(Manifest.permission.CAMERA)
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        showPicture.launch(intent)
    }

    /**
     * Отобразить картинку из медиатеки
     */
    private fun populateImage(uri: Uri) {
        val bitmap =
            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        val textToSend =
            "${userFirstName.text} ${userSurName.text} ${userBirthday.text}"

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setPackage(TELEGRAM_PACKAGE)
            type = "image/*"

            pictureUri?.let {
                putExtra(Intent.EXTRA_STREAM, it)
            }

            putExtra(Intent.EXTRA_TEXT, textToSend)
        }

        startActivity(shareIntent)
    }

    private val fillFormAct =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val data = result.data

                userFirstName.text = data?.getStringExtra("firstname")
                userSurName.text = data?.getStringExtra("surname")
                userBirthday.text = data?.getStringExtra("birthday")

                val sendText =
                    "${userFirstName.text} ${userSurName.text} ${userBirthday.text}"

                Log.d(LOG_TAG, sendText)
            }
        }

    private val setCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermit ->

            if (isPermit) {
                imageView.setImageResource(R.drawable.cat)
                return@registerForActivityResult
            }

            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                clarifyingDialog()
            } else {
                settingsDialog()
            }
        }

    private val showPicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val imgUri = result.data?.data

                imgUri?.let {
                    pictureUri = it
                    populateImage(it)
                }
            }
        }

    private fun clarifyingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Камера отключена")
            .setMessage("Необходимо настроить разрешение для камеры")
            .setPositiveButton("Настроить разрешение") { _, _ ->
                setCameraPermission.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun settingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setCancelable(true)
            .setTitle("Камера отключена")
            .setMessage("Необходимо настроить разрешение для камеры")
            .setPositiveButton("Открыть настройки") { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        "package:$packageName".toUri()
                    )
                )
            }
            .show()
    }
}