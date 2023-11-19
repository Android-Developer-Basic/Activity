package otus.gpb.homework.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

const val tagUserInfo = "user-info"
enum class PermissionState {
    NOT_ASKED,GRANTED,DENIED
}

class EditProfileActivity : AppCompatActivity(R.layout.activity_edit_profile) {

    private val TAG = "EditProfileActivity"
    private lateinit var imageView: ImageView

    private var cameraPermissionState: PermissionState = PermissionState.NOT_ASKED
    private var userData = UserDataSet()

    object Gallery {
        private lateinit var result: ActivityResultLauncher<Intent>
        private const val TAG="EditProfileActivity.Gallery"
        fun create(context: EditProfileActivity) {
            result = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
                if (r.resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Got image from camera or gallery")
                    r.getData()?.getData()?.let {
                        Log.d(TAG, "Set image from gallery")
                        context.populateImage(it)
                    }
                }
            }
        }
        fun invoke(context: EditProfileActivity) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            result.launch(intent)
        }
    }
    object Details {
        private const val TAG="EditProfileActivity.Details"
        private lateinit var result: ActivityResultLauncher<Intent>
        fun create(context: EditProfileActivity) {
            result = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
                if (r.resultCode == Activity.RESULT_OK) {
                    r.data?.getParcelableExtra<UserDataSet>(tagUserInfo)?.let {
                        Log.d(TAG, "Received data<$tagUserInfo>: {${it.toString()}")
                        context.updateUserData(it)
                    }
                }
            }
        }
        fun invoke(context: EditProfileActivity) {
            val intent = Intent(context, FillFormActivity::class.java).apply {
                putExtra(tagUserInfo, context.userData as Parcelable)
            }
            result.launch(intent)
        }
    }
    object Camera {
        private lateinit var result: ActivityResultLauncher<Intent>
        private lateinit var permissionLauncher: ActivityResultLauncher<String>
        private const val TAG="EditProfileActivity.Camera"
        fun create(context: EditProfileActivity) {
            result = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
                if (r.resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Got image from camera")
                    r.getData()?.getExtras()?.get("data")?.let {
                        Log.d(TAG, "Set image from camera")
                        context.populateImage(it as Bitmap)
                    }
                }
            }

            permissionLauncher = context.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    context.cameraPermissionState = PermissionState.GRANTED
                    Log.d(TAG, "Granted")
                    invoke(context)
                } else {
                    Log.d(TAG, "Denied")
                    Toast
                        .makeText(context, R.string.camera_access_denied, Toast.LENGTH_SHORT)
                        .show()
                    if (context.cameraPermissionState == PermissionState.NOT_ASKED) {
                        context.cameraPermissionState = PermissionState.DENIED
                    }
                }
            }
        }
        fun invoke(context: EditProfileActivity) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                context.cameraPermissionState = PermissionState.GRANTED
                Log.d(TAG, "Taking photo")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                result.launch(intent)
            } else {
                if (context.shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    Log.d(TAG, "Show rationale dialogue")
                    MaterialAlertDialogBuilder(context)
                        .setTitle(context.resources.getString(R.string.title_need_permission))
                        .setNeutralButton(context.resources.getString(R.string.cancel)) { dialog, which ->
                        }
                        .setPositiveButton(context.resources.getString(R.string.give_permission)) { dialog, which ->
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                        .show()
                } else {
                    if (context.cameraPermissionState == PermissionState.NOT_ASKED) {
                        Log.d(TAG, "Just request permissions")
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        Log.d(TAG, "Just request permissions after launcher")
                    } else {
                        Log.d(TAG, "Show open settings dialogue")
                        Toast
                            .makeText(context, R.string.camera_access_denied, Toast.LENGTH_SHORT)
                            .show()
                        MaterialAlertDialogBuilder(context)
                            .setTitle(context.resources.getString(R.string.title_open_settings))
                            .setPositiveButton(context.resources.getString(R.string.open_camera_permissions_settings)) { dialog, which ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context.packageName, null)
                                intent.data = uri
                                context.startActivity(intent)
                            }
                            .show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = findViewById(R.id.imageview_photo)

        Log.d(TAG, "OnCreate")

        Gallery.create(this)
        Camera.create(this)
        Details.create(this)

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
            Details.invoke(this)
        }

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title_choose_photo_source))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                }
                .setNegativeButton(resources.getString(R.string.choose_photo_from_gallery)) { dialog, which ->
                    Gallery.invoke(this)
                }
                .setPositiveButton(resources.getString(R.string.take_photo)) { dialog, which ->
                    Camera.invoke(this)
                }
                .show()
        }
    }


    private fun updateUserData(set: UserDataSet) {
        userData = set.copy()
        findViewById<TextView>(R.id.textview_name)
            .setText(userData.firstname)
        findViewById<TextView>(R.id.textview_surname)
            .setText(userData.lastname)
        findViewById<TextView>(R.id.textview_age).let {
            val age=userData.getAge()
            if (age.isNotEmpty()) {
                it.setText(age)
            }
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        userData.avatar = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun populateImage(bitmap: Bitmap) {
        userData.avatar = Uri.EMPTY
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (userData.avatar== Uri.EMPTY) {
            Toast
                .makeText(this, "Профиль не заполнен", Toast.LENGTH_SHORT)
                .show()
            return
        }
        try {
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, userData.avatar)
                putExtra(Intent.EXTRA_TEXT, userData.toString())
                setPackage("org.telegram.messenger")
            }.let {
                startActivity(it)
            }
        } catch (e: ActivityNotFoundException) {
            Toast
                .makeText(this, "Телеграм не найден", Toast.LENGTH_SHORT)
                .show()
        }
    }
}