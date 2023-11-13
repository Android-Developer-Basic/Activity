package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity(), OnClickListener {

    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri

    private val nameTextView by lazy {
        findViewById<TextView>(R.id.textview_name)
    }

    private val lastNameTextView by lazy {
        findViewById<TextView>(R.id.textview_surname)
    }

    private val ageTextView by lazy {
        findViewById<TextView>(R.id.textview_age)
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showRationalDialog()
                } else {
                    openAppSettings()
                }
            }
        }

    private val selectFromGallery =
        registerForActivityResult(CustomActivityResultContract()) { uri ->
            uri?.let {
                imageUri = it
                populateImage(uri)
            }
        }

    private val openFillFormForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

                intent?.let {
                    nameTextView.text = intent.getStringExtra(nameExtra)
                    lastNameTextView.text = intent.getStringExtra(lastnameExtra)
                    ageTextView.text = intent.getStringExtra(ageExtra)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)

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
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imageview_photo -> openAlertDlg()
            R.id.make_picture_btn -> createPhoto()
            R.id.choose_photo_btn -> selectPhotoFromGallery()
            R.id.button4 -> openEditActivity()
        }
    }

    private fun selectPhotoFromGallery() {
        selectFromGallery.launch("")
    }

    private fun openEditActivity() {
        val intent = Intent(this, FillFormActivity::class.java)

        openFillFormForResult.launch(intent)
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
        }

        startActivity(intent)
    }

    private fun showRationalDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Attention")
            setMessage("To make photo we need permissions")
            setCancelable(false)
            setPositiveButton("Give permission") { _, _ ->
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        }.create().show()
    }

    private fun createPhoto() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        findViewById<ImageView>(R.id.imageview_photo).setImageResource(R.drawable.cat)
    }

    private fun openAlertDlg() {
        val customView = layoutInflater.inflate(R.layout.custom_alert_dlg, null)
        customView.findViewById<Button>(R.id.make_picture_btn).setOnClickListener(this)
        customView.findViewById<Button>(R.id.choose_photo_btn).setOnClickListener(this)

        MaterialAlertDialogBuilder(this).apply {
            setTitle("Choose option")
            setMessage("Create photo or select it from photo gallery")
            setCancelable(false)
            setView(customView)
        }.create().show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val name = nameTextView.text
        val lastname = lastNameTextView.text
        val age = ageTextView.text

        val telegramIntent = Intent().apply {
            type = "*/*"
            setPackage("org.telegram.messenger")
            putExtra(Intent.EXTRA_STREAM, imageUri) // can be non initialized
            putExtra(Intent.EXTRA_TEXT, "$name $lastname $age")
        }

        try {
            startActivity(telegramIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Oops", Toast.LENGTH_LONG).show()
        }
    }
}