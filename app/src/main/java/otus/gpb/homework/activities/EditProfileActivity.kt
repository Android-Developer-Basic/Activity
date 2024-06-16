package otus.gpb.homework.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class EditProfileActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private lateinit var choosePhotoLauncher: ActivityResultLauncher<Intent>
    private lateinit var fillFormLauncher: ActivityResultLauncher<Intent>
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView

    private var permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> imageView.setImageResource(R.drawable.cat)
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ->
                showSettingsAlertDialog(this)

            else -> showAlertDialogCamera(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            showAlertDialogWithActions(this)
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
        choosePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    val selectImageUri: Uri? = result.data?.data
                    if (selectImageUri != null) {
                        populateImage(selectImageUri)
                    }
                }

            }
        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)
        val buttonEditProfile = findViewById<Button>(R.id.button4)
        buttonEditProfile.setOnClickListener {
            launchFillFormForActivityResult()
        }
        fillFormLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val name = data?.getStringExtra("name")
                    val surname = data?.getStringExtra("surname")
                    val age = data?.getStringExtra("age")
                    name?.let { textViewName.text = it }
                    surname?.let { textViewSurname.text = it }
                    age?.let { textViewAge.text = it }
                }

            }
    }

    fun showAlertDialogWithActions(context: Context) {
        val options = arrayOf(
            context.getString(R.string.take_photo),
            context.getString(R.string.choose_photo)
        )

        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.title))
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }

                    1 -> {
                        choosePhoto(context)
                    }
                }
            }
            .setNeutralButton(context.getString(R.string.cancel)) { dialog, which ->
                // Respond to neutral button press
            }
            .setPositiveButton(context.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }

    fun showAlertDialogCamera(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.title))
            .setMessage(resources.getString(R.string.explanation))
            .setNeutralButton(context.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(context.getString(R.string.accept)) { dialog, which ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            .show()
    }

    fun showSettingsAlertDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.permission_needed))
            .setMessage(context.getString(R.string.permission_denied_explanation))
            .setPositiveButton(context.getString(R.string.settings)) { _, _ ->
                val intent =
                    Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (fillFormLauncher != null) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                setType("image/*")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "name=${textViewName}\nsurname=${textViewSurname}\nage=${textViewAge} "
                )
                if (imageUri != null) putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            startActivity(intent)
        }
    }

    private fun choosePhoto(context: Context) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        choosePhotoLauncher.launch(intent)
    }

    private fun launchFillFormForActivityResult() {
        val intent = Intent(this, FillFormActivity::class.java)
        fillFormLauncher.launch(intent)
    }
}
