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
import java.time.LocalDate
import java.time.Period


const val tagUserInfo = "user-info"
enum class PermissionState {
    NOT_ASKED,GRANTED,DENIED
}


class EditProfileActivity : AppCompatActivity(R.layout.activity_edit_profile) {

    private val TAG = "EditProfileActivity"
    private lateinit var imageView: ImageView
    private lateinit var resultImage: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var resultFillForm: ActivityResultLauncher<Intent>
    private var cameraPermissionState: PermissionState = PermissionState.NOT_ASKED
    private var userData=UserDataSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = findViewById(R.id.imageview_photo)

        Log.d (TAG,"OnCreate")
        registerForPermissionLauncher()
        registerForResultImage()
        registerForFillFormResult()

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
            openFillForm()
        }

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title_choose_photo_source))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                }
                .setNegativeButton(resources.getString(R.string.choose_photo_from_gallery)) {dialog, which ->
                    selectFromGallery()
                }
                .setPositiveButton(resources.getString(R.string.take_photo)) { dialog, which ->
                    takePhoto()
                }
                .show()
        }
    }

    fun updateUserData() {
        findViewById<TextView>(R.id.textview_name).let {
            it.setText(userData.firstname)
        }
        findViewById<TextView>(R.id.textview_surname).let {
            it.setText(userData.lastname)
        }
        findViewById<TextView>(R.id.textview_age).let {
            if (userData.birthdate.year!=1900) {
                it.setText(Period.between(userData.birthdate, LocalDate.now()).years.toString())
            }

        }
    }

    private fun registerForFillFormResult() {
        resultFillForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<UserDataSet>(tagUserInfo)?.let {
                    Log.d(TAG,"Received data<$tagUserInfo>: {${it.toString()}")
                    userData=it.copy()
                    updateUserData()
                }
            }
        }
    }
    private fun openFillForm() {
        val intent = Intent(this,FillFormActivity::class.java).apply {
            putExtra(tagUserInfo, userData as Parcelable)
        }
        resultFillForm.launch(intent)

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun populateImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        try {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "image/*"
            sendIntent.putExtra(Intent.EXTRA_STREAM, userData.avatar)
            sendIntent.putExtra(Intent.EXTRA_TEXT, "some text")
            sendIntent.setPackage("org.telegram.messenger")
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            Toast
                .makeText(this,"Телеграм не найден",Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun registerForResultImage() {
        resultImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Got image from camera or gallery")
                    result.getData()?.getData()?.let {
                        Log.d(TAG, "Set image from gallery")
                        userData.avatar = it
                        populateImage(it)
                    }
                    result.getData()?.getExtras()?.get("data")?.let {
                        Log.d(TAG, "Set image from camera")
                        populateImage(it as Bitmap)
                        Log.d(TAG, "URI: ${userData.avatar}")
                    }
                }
            }
    }

    private fun selectFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultImage.launch(intent)
    }


    private fun takePhoto() {
        if (requestCameraPermission()) {
            Log.d(TAG,"Taking photo")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                userData.avatar= Uri.EMPTY
            }
            resultImage.launch(intent)
        }
    }

    private fun registerForPermissionLauncher() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                cameraPermissionState = PermissionState.GRANTED
                Log.d(TAG, "Granted")
                takePhoto()
            } else {
                Log.d(TAG, "Denied")
                Toast
                    .makeText(this, R.string.camera_access_denied, Toast.LENGTH_SHORT)
                    .show()
                if (cameraPermissionState == PermissionState.NOT_ASKED) {
                    cameraPermissionState = PermissionState.DENIED
                }
            }

        }
    }

    private fun requestCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionState = PermissionState.GRANTED
            return true
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                Log.d(TAG, "Show rationale dialogue")
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.title_need_permission))
                    .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                    }
                    .setPositiveButton(resources.getString(R.string.give_permission)) { dialog, which ->
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                    .show()
            } else {
                if (cameraPermissionState == PermissionState.NOT_ASKED) {
                    Log.d(TAG, "Just request permissions")
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    Log.d(TAG, "Just request permissions after launcher")
                } else {
                    Log.d (TAG, "Show open settings dialogue")
                    Toast
                        .makeText(this,R.string.camera_access_denied,Toast.LENGTH_SHORT)
                        .show()
                    MaterialAlertDialogBuilder(this)
                        .setTitle(resources.getString(R.string.title_open_settings))
                        .setPositiveButton(resources.getString(R.string.open_camera_permissions_settings)) { dialog, which ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .show()
                }
            }
            return false
        }
    }
}