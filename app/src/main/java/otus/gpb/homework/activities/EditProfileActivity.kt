package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var surnameTextView: TextView
    private lateinit var ageTextView: TextView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.cat)
                imageView.setImageDrawable(drawable)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    showRationaleDialog()
                } else {
                    showSettingsDialog()
                }
            }
        }

    private val pickPhoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                populateImage(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    private val fillFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val name = data?.getStringExtra("name")
            val surname = data?.getStringExtra("surname")
            val age = data?.getStringExtra("age")

            nameTextView.text = name
            surnameTextView.text = surname
            ageTextView.text = age
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        nameTextView = findViewById(R.id.textview_name)
        surnameTextView = findViewById(R.id.textview_surname)
        ageTextView = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Set a photo")
                .setMessage("Choose an action")
                .setPositiveButton("Choose from the gallery") { _, _ ->
                    pickFromTheGallery()
                }
                .setNeutralButton("Take a photo") { _, _ ->
                    requestCameraPermission()
                }
                .show()
        }

        val editProfileButton = findViewById<Button>(R.id.button4)

        editProfileButton.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            fillFormLauncher.launch(intent)
        }

        val sendButton = findViewById<Button>(R.id.send_button)
        sendButton.setOnClickListener {
            openSenderApp()
        }
    }

    private fun openSenderApp() {
        val name = nameTextView.text.toString()
        val surname = surnameTextView.text.toString()
        val age = ageTextView.text.toString()

        val message = "Name: $name\nSurname: $surname\nAge: $age"
        val drawable = imageView.drawable
        val imageFile = File(cacheDir, "profile_image.png")
        val fos = FileOutputStream(imageFile)
        (drawable as? BitmapDrawable)?.bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            imageFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, message)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setPackage("org.telegram.messenger")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Telegram is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
            this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val drawable = ContextCompat.getDrawable(this, R.drawable.cat)
                imageView.setImageDrawable(drawable)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
            this,
             Manifest.permission.CAMERA
            ) -> {
            showRationaleDialog()
            }

            else -> {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
     }
    }

    private fun showRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Why do we need your permission?")
            .setMessage("To take your profile photo, we need access to your phone's camera.")
            .setPositiveButton("Allow camera access") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Cancel", null)
                .show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("You can change permissions in the app settings")
            .setMessage("Camera access is required to take a profile photo.")
            .setNeutralButton("Go to settings") { _, _ ->
             val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
             val uri = Uri.fromParts("package", packageName, null)
             intent.data = uri
                startActivity(intent)
         }
          .show()
    }

    private fun pickFromTheGallery() {
        pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }
}
