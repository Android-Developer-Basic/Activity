package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.common.Constants
import otus.gpb.homework.model.Payload
import java.util.Objects

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editButton: Button
    private lateinit var selectPhotoActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editProfileActivityResultLauncher: ActivityResultLauncher<Intent>

    private var cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            val countCameraPermissionRequests = this.getPreferences(Context.MODE_PRIVATE)
                .getInt(R.integer.count_camera_permission_requests.toString(), 0)

            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED ->
                    imageView.setImageResource(R.drawable.cat)

                else -> this.getPreferences(Context.MODE_PRIVATE).edit()
                    .putInt(
                        R.integer.count_camera_permission_requests.toString(),
                        countCameraPermissionRequests + 1
                    ).apply()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editButton = findViewById(R.id.button4)

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

        registerPhotoActivityResultLauncher()
        registerProfileActivityResultLauncher()

        imageView.setOnClickListener { showPhotoDialog() }
        editButton.setOnClickListener { openEditProfileForm() }
    }

    private fun registerPhotoActivityResultLauncher() {
        selectPhotoActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (Objects.equals(RESULT_OK, it.resultCode) && Objects.nonNull(it.data)) {
                    val selectedImageUri = it.data?.data

                    if (Objects.nonNull(selectedImageUri)) {
                        populateImage(selectedImageUri!!)
                    }
                }
            }
    }

    private fun registerProfileActivityResultLauncher() {
        editProfileActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (Objects.equals(Activity.RESULT_OK, it.resultCode)) {
                    val result = it.data?.getParcelableExtra("payload", Payload::class.java)

                    findViewById<TextView>(R.id.textview_name).text = result?.firstname
                    findViewById<TextView>(R.id.textview_surname).text = result?.lastname
                    findViewById<TextView>(R.id.textview_age).text = result?.age
                }
            }
    }

    private fun showPhotoDialog() {
        MaterialAlertDialogBuilder(this)
            .setItems(resources.getTextArray(R.array.dialogActions)) { _, which ->
                when (which) {
                    0 -> {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            imageView.setImageResource(R.drawable.cat)
                        } else {
                            when (this.getPreferences(Context.MODE_PRIVATE)
                                .getInt(
                                    R.integer.count_camera_permission_requests.toString(),
                                    0
                                )) {
                                1 -> rationaleDialog(this)
                                2 -> settingsDialog(this)
                                else -> cameraActivityLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    }

                    1 -> selectPhotoActivityResultLauncher.launch(
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                        )
                    )
                }
            }.show()
    }

    private fun openEditProfileForm() {
        editProfileActivityResultLauncher.launch(
            Intent(this, FillFormActivity::class.java)
        )
    }

    private fun rationaleDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage(context.getString(R.string.camera_rationale))
            .setPositiveButton(context.getString(R.string.grant_access)) { _, _ ->
                cameraActivityLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun settingsDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage(context.getString(R.string.camera_explanation))
            .setPositiveButton(context.getString(R.string.settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.setData(Uri.fromParts("package", packageName, null))

                startActivity(intent)
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
        startActivity(Intent().apply {
            action = Intent.ACTION_SEND
            addCategory(Intent.CATEGORY_DEFAULT)
            type = "text/plain"

            val payload = Payload(
                findViewById<TextView>(R.id.textview_name).text.toString(),
                findViewById<TextView>(R.id.textview_surname).text.toString(),
                findViewById<TextView>(R.id.textview_age).text.toString(),
                getCompressedPhoto()
            )

            putExtra("payload", payload)
        })
    }

    private fun getCompressedPhoto(): Bitmap {
        val photo = imageView.drawable.current

        val bitmap = Bitmap.createBitmap(
            photo.intrinsicWidth,
            photo.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        photo.setBounds(0, 0, canvas.width, canvas.height)
        photo.draw(canvas)

        return Bitmap.createScaledBitmap(
            bitmap,
            Constants.BITMAP_SIZE,
            Constants.BITMAP_SIZE,
            false
        )
    }
}
