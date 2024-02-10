package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonOpenFillForm: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewLastName: TextView
    private lateinit var textViewAge: TextView


    private var countsDeniedPermission = 0
    private val launcherFillFormActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            textViewName.text = it.data?.getStringExtra(FIRST_NAME)
            textViewLastName.text = it.data?.getStringExtra(LAST_NAME)
            textViewAge.text = it.data?.getStringExtra(AGE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        buttonOpenFillForm = findViewById(R.id.button4)
        textViewName = findViewById(R.id.textview_name)
        textViewLastName = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)

        buttonOpenFillForm.setOnClickListener() {
            launcherFillFormActivity.launch(Intent(this, FillFormActivity::class.java))
        }

        imageView.setOnClickListener() {
            alertDialogMain()
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
    }
    private var uriImg: Uri? = null
    private val activityImagesLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri:Uri? -> uri?.let {
                imageUri ->
                uriImg = imageUri
               populateImage(imageUri)
    }
    }
    private fun alertDialogMain() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Выберите действие:")
        var selected = -1
        alertBuilder.setSingleChoiceItems(arrayOf("Сделать фото", "Выбрать фото"), -1) {
            dialog, which ->
            when {
                which == 0 -> {selected = 0}
                which == 1 -> {selected = 1}
            }
        }
        alertBuilder.setPositiveButton("OK") { dialog, which ->
            when {
                selected == 0 -> {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && countsDeniedPermission < 2) {
                        requestPermissionCam.launch(Manifest.permission.CAMERA)
                        selected = -1
                    }
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        alertDialogAskPermission()
                    }
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && countsDeniedPermission >= 2) {
                        alertDialogOpenSetting()
                    }
                }
                selected == 1 -> {
                    activityImagesLauncher.launch("image/*")
                    selected = -1
                }
            }
        }
        alertBuilder.setNegativeButton("Cancel", null)

        val dialog = alertBuilder.create()
        dialog.show()
    }
    private fun alertDialogAskPermission() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setMessage("Доступ к камере необходим для использования функционала приложения - делать фотографии")
        alertBuilder.setPositiveButton("Дать доступ") {
            dialog, which ->
            requestPermissionCam.launch(Manifest.permission.CAMERA)
        }
        alertBuilder.setNegativeButton("Отмена", null)
        val dialog = alertBuilder.create()
        dialog.show()
    }
    private fun alertDialogOpenSetting() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setMessage("Доступ к камере необходим для использования функционала приложения - разрешите доступ через настройки")
        alertBuilder.setPositiveButton("Перейти к настройкам") {
                dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        alertBuilder.setNegativeButton("Отмена", null)
        val dialog = alertBuilder.create()
        dialog.show()
    }

    private val requestPermissionCam = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted ->
                when {
                    isGranted -> { imageView.setImageResource(R.drawable.cat) }
                    !isGranted -> {countsDeniedPermission++}
                }
            }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (textViewName.text.isEmpty() || textViewLastName.text.isEmpty() || textViewAge.text.isEmpty()) {
            Toast.makeText(this, "Выбраны не все данные!", Toast.LENGTH_LONG ).show()
        }
        else {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            val text = textViewName.text.toString() + "\n" + textViewLastName.text.toString() + "\n" + textViewAge.text.toString()
            intent.setPackage("org.telegram.messenger")
            intent.putExtra(Intent.EXTRA_TEXT, text)
            if (uriImg != null) {intent.putExtra(Intent.EXTRA_STREAM, uriImg)}
            startActivity(Intent.createChooser(intent, "Share with"))
        }
    }

    companion object {
        val FIRST_NAME = "Name"
        val LAST_NAME = "LastName"
        val AGE = "Age"
    }
}

