package otus.gpb.homework.activities

import android.Manifest.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button4: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewSurName: TextView
    private lateinit var textViewNameAge: TextView
    private var imageUri: Uri = Uri.EMPTY

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val resultCode = result.resultCode
        if (resultCode == RESULT_OK && data != null) {
            textViewName.text = data.getStringExtra(NAME_KEY)
            textViewSurName.text = data.getStringExtra(SURNAME_KEY)
            textViewNameAge.text = data.getStringExtra(AGE_KEY)

        } else {
            Snackbar.make(imageView, "Вы ничего не ввели", 2000).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        button4 = findViewById(R.id.button4)
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
        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title))
                .setMessage(resources.getString(R.string.select_an_action))
                .setNeutralButton(resources.getString(R.string.take_a_photo)) { dialog, which ->
                    val rew = shouldShowRequestPermissionRationale(permission.CAMERA)
                    if (!rew) {
                        permissionCamera.launch(permission.CAMERA)
                    } else {
                        MaterialAlertDialogBuilder(this)
                            .setTitle(resources.getString(R.string.camera_access))
                            .setMessage(resources.getString(R.string.why_get_foto))
                            .setPositiveButton(resources.getString(R.string.give_access)) { _, _ ->
                                permissionCamera.launch(permission.CAMERA)
                            }
                            .setNeutralButton(resources.getString(R.string.cancellation)) { _, _ ->
                            }
                            .show()
                    }
                }
                .setPositiveButton(resources.getString(R.string.choose_a_photo)) { _, _ ->
                    takePictureUri.launch("image/*")

                }
                .show()
        }
        button4.setOnClickListener {
            launcher.launch(Intent(this, FillFormActivity::class.java))


        }
    }

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }

            !shouldShowRequestPermissionRationale(permission.CAMERA) -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.camera_access))
                    .setMessage(resources.getString(R.string.why_get_foto))
                    .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }
                    .show()
            }
            else -> {
            }
        }
    }
    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { image ->
        imageView.setImageBitmap(image)

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageUri = uri
    }

    private fun openSenderApp() {
        val intentForTG = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            setPackage("org.telegram.messenger")
            action = Intent.ACTION_SEND
            if (!Uri.EMPTY.equals(imageUri)) {
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            putExtra(
                Intent.EXTRA_TEXT,
                "${textViewName.text}\n${textViewSurName.text}\n${textViewNameAge.text}"
            )
        }
        startActivity(intentForTG)
    }
}