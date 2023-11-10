package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

enum class EDIT_FIELD {
    NAME, SURNAME, AGE
}

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var surnameView: TextView
    private lateinit var ageView: TextView
    private lateinit var editBtn: Button
    private lateinit var uriForSend: Uri

    private val choosePhoto: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let { populateImage(it) }
        }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitMap ->
            bitMap?.let { imageView.setImageBitmap(bitMap) }
        }

    private val editProfileContract = registerForActivityResult(EditProfileContract()) {
        nameView.text = it?.get(EDIT_FIELD.NAME)
        surnameView.text = it?.get(EDIT_FIELD.SURNAME)
        ageView.text = it?.get(EDIT_FIELD.AGE)
    }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // Permission получен
                    takePhoto.launch(null)
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Вы запретили доступ к камере!")
                        .setMessage(resources.getString(R.string.get_reason))
                        .setNeutralButton(resources.getString(R.string.stop)) { dialog, which ->
                            {}
                        }
                        .setNegativeButton(resources.getString(R.string.open_settings)) { dialog, which ->
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    val uri = Uri.fromParts("package", packageName, null)
                                    data = uri
                                }
                            startActivity(intent)
                        }
                        .show()
                }

                else -> {
                    // Пермишен не получен, без "не спрашивать больше"
                    Toast.makeText(this, "Без доступа к камере невозможно снять фото", Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        nameView = findViewById(R.id.name_view)
        surnameView = findViewById(R.id.surname_view)
        ageView = findViewById(R.id.age_view)

        editBtn = findViewById(R.id.editProfileBtn)

        editBtn.setOnClickListener {
            editProfileContract.launch(
                mapOf(
                    EDIT_FIELD.NAME to (nameView.text ?: ""),
                    EDIT_FIELD.SURNAME to (surnameView.text ?: ""),
                    EDIT_FIELD.AGE to (ageView.text ?: ""),
                )
            )
        }

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(resources.getString(R.string.choose_avatar))
                .setNeutralButton(resources.getString(R.string.choose_photo)) { dialog, which ->
                    choosePhoto.launch("image/*")
                }
                .setPositiveButton(resources.getString(R.string.take_photo)) { dialog, which ->
                    val isGranted = ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                    if (!isGranted) {
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }
                }
                .show()

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
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        uriForSend = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        try {
//            intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=balashovaai"));
            intent = Intent(Intent.ACTION_SEND) // Нам нужна программа, которая примет наш текст и картинку
                .apply {
                    putExtra( // Объединяем наши поля
                        Intent.EXTRA_TEXT,
                        "ИМЯ: ${nameView.text}\nФАМИЛИЯ: ${surnameView.text}"
                    )
                    type = "image/*" // Указываем, что посылаем картинку
                    putExtra(Intent.EXTRA_STREAM, uriForSend) // Путь к картинке
                    setPackage("org.telegram.messenger") // Наш телеграм
                }
            startActivity(intent);
        } catch (e: Exception) {
            e.message?.let { Log.d("openSenderApp", it) }
        }
    }

    companion object {
        val RESULT_KEY = "result"
    }

}