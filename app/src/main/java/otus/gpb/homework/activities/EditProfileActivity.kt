package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var surnameView: TextView
    private lateinit var ageView: TextView
    private lateinit var imageView: ImageView
    private lateinit var buttonEdit: Button
    private var imageUri: Uri? = null
    private var alreadyDenied = false




    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    imageView.setImageResource(R.drawable.cat)
                    imageUri = Uri.parse("android.resource://$packageName/${R.drawable.cat}")
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    if(alreadyDenied) {
                        openSettings()
                    }
                    else {
                        alreadyDenied = true
                    }
                }
            }
        }

    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        it?.let { populateImage(it) }
    }

    private val fillFormContract = registerForActivityResult(ContractEditProfileAndFillForm()) {
        it?.let {
            nameView.text = it.name
            surnameView.text = it.surname
            ageView.text = it.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        nameView = findViewById(R.id.textview_name)
        surnameView = findViewById(R.id.textview_surname)
        ageView = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            showPhotoDialog()
        }

        buttonEdit = findViewById(R.id.edit)
        buttonEdit.setOnClickListener {
            onEdit()
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

    private fun showPhotoDialog() {
        val items = arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo))
        MaterialAlertDialogBuilder(this)
            .setTitle("Фото")
            .setItems(items) { dialog, item ->
                when (item) {
                    0 -> onMakePhoto()
                    1 -> onChoosePhoto()
                }
            }
            .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, which ->
            }
            .show()
    }

    private fun onMakePhoto() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            onRepeatedPermission()
            return
        }
        permission.launch(Manifest.permission.CAMERA)
    }

    private fun onChoosePhoto() {
        galleryContract.launch("image/*")
    }

    private fun onEdit() {
        fillFormContract.launch(
            PersonDTO(
                nameView.text.toString(),
                surnameView.text.toString(),
                ageView.text.toString()
            )
        )
    }

    private fun onRepeatedPermission() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.alert_title))
            .setMessage(getString(R.string.alert_message))
            .setNegativeButton(getString(R.string.alert_cancel)) { dialog, which ->
            }
            .setPositiveButton(getString(R.string.alert_positive)) { dialog, which ->
                permission.launch(Manifest.permission.CAMERA)
            }
            .show()
    }

    private fun openSettings() {
        MaterialAlertDialogBuilder(this)
            .setPositiveButton(getString(R.string.open_settings)) { dialog, which ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    data = Uri.fromParts("package", packageName, null)
                })
            }
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
        val telegramIntent = Intent(Intent.ACTION_SEND)
        telegramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        telegramIntent.setPackage("org.telegram.messenger")
        telegramIntent.putExtra(
            Intent.EXTRA_TEXT,
            "${nameView.text}\n${surnameView.text}\n${ageView.text}"
        )
        telegramIntent.type = "image/*"
        telegramIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

        start(telegramIntent, getString(R.string.telegram_error))
    }

    private fun start(intent: Intent, errorMessage: String) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.d("SENDER log", "$errorMessage: ActivityNotFoundException")
        }
    }
}