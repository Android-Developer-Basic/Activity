package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
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
import java.security.Permissions
import java.util.jar.Manifest

class EditProfileActivity : AppCompatActivity() {

    private var countsDeniedPermission = 0
    private lateinit var imageView: ImageView
    private lateinit var buttonOpen: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewSurName: TextView
    private lateinit var textViewAge: TextView
    private val options = arrayOf("Сделать фото", "Выбрать фото")

    private val requestPermissionCam = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
        when {
            isGranted -> { imageView.setImageResource(R.drawable.cat) }
            !isGranted -> {countsDeniedPermission++}
        }
    }

    private var uriImg: Uri? = null
    private val getCommentMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                uriImg = imageUri
                populateImage(imageUri)
            }
        }

    private val launcherFillFormActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            var formData: FormData?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                formData = it.data?.getParcelableExtra("data", FormData::class.java)
            } else {
                formData = it.data?.getParcelableExtra<FormData>("data")
            }

            textViewName.text = formData?.name
            textViewSurName.text = formData?.surName
            textViewAge.text = formData?.year

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textViewName = findViewById(R.id.textview_name)
        textViewSurName = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)
        buttonOpen = findViewById(R.id.button4)

        buttonOpen.setOnClickListener {
            launcherFillFormActivity.launch(Intent(this, FillFormActivity::class.java))
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

        imageView.setOnClickListener{
            showDialog()
        }
    }

    fun showDialog() {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Выберите фото")
        alertBuilder.setItems(options){ dialog, which ->
          if (which == 0) {
              makePhoto()
          } else {
              choosePhoto()
          }
        }.show()

    }

    fun makePhoto() {
        if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) && countsDeniedPermission < 2) {
            requestPermissionCam.launch(android.Manifest.permission.CAMERA)
        }
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            alertDialogAskPermission()
        }
        if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) && countsDeniedPermission >= 2) {
            alertDialogOpenSetting()
        }
    }

    private fun alertDialogAskPermission() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setMessage("Доступ к камере необходим для использования функционала приложения - делать фотографии")
        alertBuilder.setPositiveButton("Дать доступ") {
                dialog, which ->
            requestPermissionCam.launch(android.Manifest.permission.CAMERA)
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

    fun choosePhoto() {
        getCommentMedia.launch("image/*")
    }



    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        uriImg = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (textViewName.text.isEmpty() || textViewSurName.text.isEmpty() || textViewAge.text.isEmpty()) {
            Toast.makeText(this, "Выбраны не все данные!", Toast.LENGTH_LONG ).show()
        }
        else {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            val text = buildString {
                append(textViewName.text.toString())
                append("\n")
                append(textViewSurName.text.toString())
                append("\n")
                append(textViewAge.text.toString())
                append("\n")
            }
            intent.setPackage("org.telegram.messenger")
            intent.putExtra(Intent.EXTRA_TEXT, text)
            if (uriImg != null) {intent.putExtra(Intent.EXTRA_STREAM, uriImg)}
            startActivity(Intent.createChooser(intent, "Share with"))
        }
    }
}