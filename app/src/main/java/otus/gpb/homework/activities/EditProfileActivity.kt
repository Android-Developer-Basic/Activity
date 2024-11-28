package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditProfileActivity : AppCompatActivity() {

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts
            .RequestPermission()
    )
    { granted ->
        when {
            granted -> imageView.setImageResource(R.drawable.cat)
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showRationalDialog()
            else -> showSettingsDialog()
        }
    }

    private val imageContent = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        it?.let {
            profileImageUri = it
            populateImage(it)
        }
    }

    private val launcherProfileContract = registerForActivityResult(ProfileContractActivity())
    { result ->
        profile = result
        findViewById<TextView>(R.id.textview_name).text = result?.name
        findViewById<TextView>(R.id.textview_surname).text = result?.surname
        findViewById<TextView>(R.id.textview_age).text = result?.age
    }

    private var profile: Profile? = null
    private var profileImageUri: Uri? = null
    private lateinit var imageView: ImageView

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

        imageView.setOnClickListener {
            showSelectPictureAlert()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            launcherProfileContract.launch(Unit)
        }
    }


    private fun showSelectPictureAlert() = AlertDialog.Builder(this)
        .setTitle(resources.getString(R.string.choose_image_title))
        .setItems(
            arrayOf(
                resources.getString(R.string.take_photo_dialog_button),
                resources.getString(R.string.choose_photo_dialog_button)
            )
        ) { _, which ->
            when (which) {
                0 -> setPhoto()
                1 -> getImage()
            }
        }
        .create()
        .show()

    private fun showRationalDialog() = AlertDialog.Builder(this)
        .setTitle(resources.getString(R.string.camera_permission_title))
        .setMessage(resources.getString(R.string.rational_dialog_message))
        .setPositiveButton(resources.getString(R.string.give_permission_dialog_button)) { _, _ ->
            setPhoto()
        }
        .setNegativeButton(resources.getString(R.string.cancel_dialog_button)) { dialog, _ ->
            dialog.cancel()
        }
        .create()
        .show()

    private fun showSettingsDialog() = AlertDialog.Builder(this)
        .setTitle(resources.getString(R.string.go_to_settings_dialog_message))
        .setPositiveButton(resources.getString(R.string.open_settings_screen_dialog_button)) { _, _ ->
            val settingIntent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", packageName, null)
            }
            startActivity(settingIntent)
        }
        .create()
        .show()

    private fun setPhoto() {
        requestCameraPermission.launch(Manifest.permission.CAMERA)
    }

    private fun getImage() {
        imageContent.launch("image/*")
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        if (profile != null) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                setType("image/*")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${profile?.name}\n${profile?.surname}\n${profile?.age}"
                )

                profileImageUri?.let {
                    putExtra(Intent.EXTRA_STREAM, profileImageUri)
                }
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.telegram_alert_message),
                    LENGTH_SHORT
                ).show()
            }
        }
        else Toast.makeText(this, resources.getString(R.string.fill_profile_info_message), LENGTH_SHORT)
            .show()
    }
}
