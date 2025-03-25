package otus.gpb.homework.activities

import android.Manifest
import android.content.DialogInterface
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewSurName: TextView
    private lateinit var textViewNameAge: TextView
    private var imageUri: Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textViewName = findViewById(R.id.textview_name)
        textViewSurName = findViewById(R.id.textview_surname)
        textViewNameAge = findViewById(R.id.textview_age)

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

        findViewById<ImageView>(R.id.imageview_photo).apply {
            setOnClickListener {
                makeImage()
            }
        }

        findViewById<Button>(R.id.button4).apply {
            setOnClickListener {
                fillForm.launch(Intent(context, FillFormActivity::class.java)
                    .putExtra(FillFormActivity.KEY_NAME, textViewName.text)
                    .putExtra(FillFormActivity.KEY_SURNAME, textViewSurName.text)
                    .putExtra(FillFormActivity.KEY_AGE, textViewNameAge.text)
                )
            }
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
        if (textViewName.text.toString().isNotBlank() ||
            textViewSurName.text.toString().isNotBlank()) {

            val intentTelegram = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                setPackage("org.telegram.messenger")
                if (imageUri != Uri.EMPTY) {
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                }
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${textViewName.text}\n${textViewSurName.text}\n${textViewNameAge.text}"
                )
            }
            startActivity(intentTelegram)
        }
        else {
            Toast.makeText(this, "Введите имя или фамилию", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeImage() {
        MaterialAlertDialogBuilder(this)
            .setItems(
                arrayOf(
                    resources.getText(R.string.take_photo),
                    resources.getText(R.string.select_photo)
                ), ::onSelPhotoMethod
            )
            .show()
    }

    private fun onSelPhotoMethod(dialog : DialogInterface, which: Int) {
        when (which) {
            0 -> {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED) {
                    takeShot()
                }
                else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        MaterialAlertDialogBuilder(this)
                            .setMessage(resources.getString(R.string.access_camera_reason))
                            .setPositiveButton(R.string.give_access, ::onGiveCameraAccess)
                            .setNegativeButton(R.string.deny_access, null)
                            .show()
                    }
                    else {
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }
                }
            }
            else -> selectImage()
        }
    }

    private fun showAccessCameraReason() {
        MaterialAlertDialogBuilder(this)
            .setMessage(resources.getString(R.string.access_camera_reason))
            .setNeutralButton(R.string.go_access_settings, ::onGoAccessSettings)
            .show()
    }

    private fun onGiveCameraAccess(dialog : DialogInterface, which: Int) {
        permissionCamera.launch(Manifest.permission.CAMERA)
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        granted->when {
            granted->takeShot()
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)->showAccessCameraReason()
            else-> {
                //Toast.makeText(this, "Need CAMERA permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onGoAccessSettings(dialog : DialogInterface, which: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private fun takeShot() = findViewById<ImageView>(R.id.imageview_photo)
        .setImageResource(R.drawable.cat)

    private fun selectImage() = takePicture.launch("image/*")

    private val takePicture = registerForActivityResult(ActivityResultContracts.GetContent()) {
        //image->imageView.setImageURI(image)
        uri->
        if (uri != null) {
            populateImage(uri)
            imageUri = uri
        }
    }

    private val fillForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            textViewName.text = data.getStringExtra(FillFormActivity.KEY_NAME)
            textViewSurName.text = data.getStringExtra(FillFormActivity.KEY_SURNAME)
            textViewNameAge.text = data.getStringExtra(FillFormActivity.KEY_AGE)
        }
    }
}