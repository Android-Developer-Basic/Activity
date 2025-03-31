package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.time.Duration

class EditProfileActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var editButton: Button
    private lateinit var nameTw: TextView
    private lateinit var lastnameTw: TextView
    private lateinit var ageTw: TextView
    private var imageUri: Uri = Uri.EMPTY

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val resultCode = result.resultCode
        if (resultCode == RESULT_OK && data != null) {
            nameTw.text = data.getStringExtra(NAME_KEY)
            lastnameTw.text = data.getStringExtra(LASTNAME_KEY)
            ageTw.text = data.getStringExtra(AGE_KEY)

        } else {
            Toast.makeText(this, "Вы не ввели всех данных", Toast.LENGTH_LONG)
        }
    }

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.i)
            }

            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.camera_access))
                    .setMessage(resources.getString(R.string.photo_reason))
                    .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }
                    .show()
            }
            else -> {  }
        }
    }

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        editButton = findViewById(R.id.button4)
        nameTw = findViewById(R.id.textview_name)
        lastnameTw = findViewById(R.id.textview_surname)
        ageTw = findViewById(R.id.textview_age)

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

        editButton.setOnClickListener {
            launcher.launch(Intent(this, FillFormActivity::class.java))
        }

        imageView.setOnClickListener {
            showImageSelectDialog()
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageUri = uri
    }

    private fun showImageSelectDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.select_an_action))
            .setNeutralButton(resources.getString(R.string.take_a_photo)) { dialog, which ->
                val rew = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

                if (!rew) {
                    permissionCamera.launch(Manifest.permission.CAMERA)
                } else {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(resources.getString(R.string.camera_access))
                        .setMessage(resources.getString(R.string.photo_reason))
                        .setPositiveButton(resources.getString(R.string.give_access)) { _, _ ->
                            permissionCamera.launch(Manifest.permission.CAMERA)
                        }
                        .setNeutralButton(resources.getString(R.string.cancellation)) { _, _ ->  }
                        .show()
                }
            }
            .setPositiveButton(resources.getString(R.string.choose_a_photo)) { _, _ ->
                takePictureUri.launch("image/*")
            }
            .show()
    }

    private fun openSenderApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            setPackage("org.telegram.messenger")
            action = Intent.ACTION_SEND
            if (!Uri.EMPTY.equals(imageUri)) {
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            putExtra(
                Intent.EXTRA_TEXT,
                "${nameTw.text}\n${lastnameTw.text}\n${ageTw.text}"
            )
        }

        startActivity(shareIntent)
    }
}