package otus.gpb.homework.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream
import androidx.core.net.toUri

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var isSecondCameraAccessAttempt: Boolean = false

    private val choosePhotoDialog
        get() = MaterialAlertDialogBuilder(this)
            .setItems(
                arrayOf(
                    resources.getString(R.string.take_photo),
                    resources.getString(R.string.choose_photo),
                )
            ) { d, w ->
                when (w) {
                    0 -> {
                        Log.i("TAG", "Take photo")
                        requestCameraAccess()
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            takePhoto()
                        }
                    }

                    1 -> {
                        Log.i("TAG", "Choose photo")
                        selectPhoto()
                    }
                }
            }.create()

    private val requestCameraPermissionDialog
        get() = MaterialAlertDialogBuilder(this)
            .setMessage("Камера нужна для создания аватара")
            .setPositiveButton(resources.getString(R.string.allow_access)) { d, w ->
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }.let { startActivity(it) }
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { d, w ->
                Log.i("TAG", "no access")
            }
            .create()

    private val requestCameraAccessLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        when {
            isGranted -> setCatToImageView()
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                if (!isSecondCameraAccessAttempt) {
                    isSecondCameraAccessAttempt = true
                    requestCameraPermissionDialog.show()
                } else {
                    MaterialAlertDialogBuilder(this)
                        .setPositiveButton(resources.getString(R.string.to_app_settings)) { d, w ->
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", packageName, null)
                            }.let { startActivity(it) }
                        }.show()

                }
            }

        }
    }

    private fun requestCameraAccess() {
        requestCameraAccessLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun setCatToImageView() {
        imageView.setImageDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.cat, null)
        )
    }

    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes) // Compress the bitmap to JPEG format
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Image Title", null)
        return path?.toUri()
    }

    private val takePhoneLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { picture ->
        imageView.setImageBitmap(picture)
        picture?.let {
            imageView.tag = getImageUriFromBitmap(this,it)
        }
    }

    private fun takePhoto() = takePhoneLauncher.launch()

    private val selectPictureLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { result ->
        result?.let { populateImage(it) }
    }

    private fun selectPhoto() = selectPictureLauncher.launch(
        PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
    )

    private val fillUserDataLauncher = registerForActivityResult(FillFormContract()) { userInfo ->
        userInfo?.name?.let { findViewById<TextView>(R.id.textview_name).text = it }
        userInfo?.surName?.let { findViewById<TextView>(R.id.textview_surname).text = it }
        userInfo?.age?.let { findViewById<TextView>(R.id.textview_age).text = it.toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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

        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener {
            choosePhotoDialog.show()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            fillUserDataLauncher.launch()
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageView.tag = uri
    }

    private fun openSenderApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            setPackage("org.telegram.messenger")
            setType("image/*")
            val imgUri = imageView.tag as? Uri
            if (imgUri != null) putExtra(Intent.EXTRA_STREAM, imgUri)
            putExtra(Intent.EXTRA_TEXT, "Имя : ${ findViewById<TextView>(R.id.textview_name).text}\nФамилия: ${ findViewById<TextView>(R.id.textview_surname).text}\nВозраст: ${ findViewById<TextView>(R.id.textview_age).text}")
        }
        try {
            startActivity(intent)
        } catch (e: Throwable) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}