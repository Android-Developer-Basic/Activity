package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class EditProfileActivity : AppCompatActivity() {
    companion object {
        const val PROFILE_NAME = "PROFILE_NAME"
        const val PROFILE_SURNAME = "PROFILE_SURNAME"
        const val PROFILE_AGE = "PROFILE_AGE"
    }

    private lateinit var imageView: ImageView
    private val dialogSingleChoiceItems = arrayOf("Сделать фото", "Выбрать фото")

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

        imageView.setOnClickListener { it ->
            var indexChecked: Int = -1
            val dialog = MaterialAlertDialogBuilder(it.context)
                .setTitle(R.string.choice_dialog_title)
                .setNegativeButton(R.string.cancel_button) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.confirm_button) { _, _ ->
                    when(indexChecked) {
                        0 -> showPermissionRequestDialog()
                        1 -> resultImageContent.launch("image/*")
                        else -> Log.d("app", "nothing")
                    }
                }
                .setSingleChoiceItems(dialogSingleChoiceItems, -1) { dialog, which ->
                    (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    which.also { indexChecked = it }
                }
                .create()

            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        findViewById<Button>(R.id.button4).setOnClickListener() {
            val intent = Intent(this, FillFormActivity::class.java)
            launcher.launch(intent)
        }
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> updateImage()
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showSettings()
            else -> showSnackbar(getString(R.string.permission_camera_not_granted))
        }
    }
    private val resultImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        val uri = result ?: Uri.EMPTY
        populateImage(uri)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> result ?: return@registerForActivityResult

        if(result.resultCode == Activity.RESULT_OK) {
            result.data?.extras?.let { extras ->
                var text = extras.getString(PROFILE_NAME).toString()
                findViewById<TextView>(R.id.textview_name).text = text

                text = extras.getString(PROFILE_SURNAME).toString()
                findViewById<TextView>(R.id.textview_surname).text = text

                text = extras.getString(PROFILE_AGE).toString()
                findViewById<TextView>(R.id.textview_age).text = text
            }
        }
    }

    private fun showPermissionRequestDialog() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            MaterialAlertDialogBuilder(this)
                .setIcon(android.R.drawable.ic_menu_camera)
                .setMessage(R.string.camera_request_dialog_title)
                .setNegativeButton(R.string.cancel_button) { dialog, _ ->
                    showSnackbar(getString(R.string.permission_camera_denied))
                    dialog.cancel()
                }
                .setPositiveButton(R.string.grant_permission_button) { dialog, _ ->
                    permissionCamera.launch(Manifest.permission.CAMERA)
                }
                .show()
        } else {
            permissionCamera.launch(Manifest.permission.CAMERA)
        }
    }
    private fun showSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
    private fun updateImage() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)
        imageView.setImageBitmap(bitmap)
        showSnackbar(getString(R.string.image_load_success))
    }
    private fun showSnackbar(text: String) {
        Snackbar.make(this.imageView, text, Snackbar.LENGTH_SHORT)
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
        try {
            val profile = getProfile()
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                putExtra(Intent.EXTRA_TEXT, profile.first)
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, profile.second.rowBytes)
            }
            startActivity(intent)
        }
        catch (exception: ActivityNotFoundException) {
            showSnackbar("Telegram application not found!")
        }
    }
    private fun getProfile(): Pair<String, Bitmap> {
        val name = findViewById<TextView>(R.id.textview_name).text
        val surname = findViewById<TextView>(R.id.textview_surname).text
        val age = findViewById<TextView>(R.id.textview_age).text
        val text = "Name: $name, Surname: $surname, Age: $age"

        val imageView = findViewById<ImageView>(R.id.imageview_photo)
        val image = imageView.drawable.toBitmap()

        return Pair(text, image)
    }
}