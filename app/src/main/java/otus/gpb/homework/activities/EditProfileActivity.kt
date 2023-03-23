package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var firstPermisionRequest = true
    private var userInfo: UserInfo = UserInfo("", "", "", null)
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                setCatImgAsPic()
            }
        }


    private val galleryContract =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { pic ->
            if (pic != null) {
                populateImage(pic)
                userInfo.picUri = pic
            }
        }
    private val fillFormActivityLauncher = registerForActivityResult(FillFormContract()) { result ->
        if (result != null) {
            val uri = userInfo.picUri
            userInfo = result
            userInfo.picUri = uri
            updateFields()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        setListeners()
    }

    private fun changeImage() {
        val options = arrayOf(
            getString(R.string.take_photo_option_name),
            getString(R.string.open_gallery_option_name)
        )
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.change_photo_dialog_title))
            .setItems(options) { _, option ->
                when (option) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .setNegativeButton(getString(R.string.cancel_button_name)) { _, _ ->
                //just closing dialog
            }
            .show()
    }

    private fun getContentString() =
        "Name: ${userInfo.name}\nSurname: ${userInfo.surname}\nAge: ${userInfo.age}"

    private fun openCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setCatImgAsPic()
            }
            firstPermisionRequest -> {
                permissionRequest.launch(Manifest.permission.CAMERA)
                firstPermisionRequest = false
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showRationalDialog()
            }
            else -> {
                showExplanationsDialog()
            }
        }

    }

    private fun openGallery() = galleryContract.launch(
        PickVisualMediaRequest.Builder()
            .setMediaType(ImageOnly)
            .build()
    )

    private fun openFillForm() = fillFormActivityLauncher.launch(userInfo)
    private fun openSenderApp() = startActivity(
        Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, getContentString())
            .setType("image/*")
            .putExtra(Intent.EXTRA_STREAM, userInfo.picUri)
            .setPackage("org.telegram.messenger")
    )

    private fun openSetting() = startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun setCatImgAsPic() {
        findViewById<ImageView>(R.id.imageview_photo).setImageResource(R.drawable.cat)
        userInfo.picUri = Uri.parse("android.resource://$packageName/${R.drawable.cat}")
    }

    private fun setListeners() {
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
            changeImage()
        }
        findViewById<Button>(R.id.button_edit_profile).setOnClickListener() {
            openFillForm()
        }
    }

    private fun showExplanationsDialog() = MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.settings_dialog_title))
        .setMessage(getString(R.string.settings_dialog_message))
        .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
            openSetting()
        }.setNegativeButton(getString(R.string.cancel_button_name)) { _, _ ->
            //just closing dialog
        }
        .show()

    private fun showRationalDialog() = MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.rational_dialog_title))
        .setMessage(getString(R.string.rational_dialog_message))
        .setPositiveButton(getString(R.string.access_button_name)) { _, _ ->
            permissionRequest.launch(Manifest.permission.CAMERA)
        }.setNegativeButton(getString(R.string.cancel_button_name)) { _, _ ->
            //just closing dialog
        }
        .show()

    private fun updateFields() {
        findViewById<TextView>(R.id.textview_name).text = userInfo.name
        findViewById<TextView>(R.id.textview_surname).text = userInfo.surname
        findViewById<TextView>(R.id.textview_age).text = userInfo.age
    }
}