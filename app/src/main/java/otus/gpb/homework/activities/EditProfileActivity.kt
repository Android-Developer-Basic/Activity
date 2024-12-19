package otus.gpb.homework.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_VIEW
import android.content.Intent.EXTRA_TEXT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textviewName: TextView
    private lateinit var textviewSurname: TextView
    private lateinit var textviewAge: TextView
    private lateinit var button4: Button
    private var counter: Int = 0
    private var uriToImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textviewName = findViewById(R.id.textview_name)
        textviewSurname = findViewById(R.id.textview_surname)
        textviewAge = findViewById(R.id.textview_age)
        button4 = findViewById(R.id.button4)

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
            MaterialAlertDialogBuilder(this)
                .setNeutralButton(resources.getString(R.string.select_photo)) { dialog, which ->
                    permissionGallery.launch("image/*")
                }
                .setPositiveButton(resources.getString(R.string.create_photo)) { _, _ ->
                    if (counter > 0) {
                        AlertDialog.Builder(this)
                            .setMessage("Необходим доступ к камере что бы сделать фотографию")
                            .setPositiveButton("Дать доступ") { _, _ ->
                                permissionCamera.launch(Manifest.permission.CAMERA)
                            }
                            .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                            .create()
                            .show()
                    } else {
                        counter++
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }
                }
                .show()
        }

        button4.setOnClickListener {
            startForResult.launch(Intent(this, FillFormActivity::class.java))
        }

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        uriToImage = uri
        imageView.setImageBitmap(bitmap)
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun openSenderApp() {
        val intent = Intent(ACTION_SEND).apply {
            putExtra(
                EXTRA_TEXT,
                "Имя ${textviewName.text}. Фамилия ${textviewSurname.text}. Возраст ${textviewAge.text}"
            )
            uriToImage?.apply {
                putExtra(Intent.EXTRA_STREAM, uriToImage)
            }
            type = "image/*"
            setPackage("org.telegram.messenger")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val chooser = Intent.createChooser(intent, "Поделиться профилем")
            startActivity(chooser)
        }

    }

    private val permissionGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.apply {
                populateImage(it)
            }
        }

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }

            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                MaterialAlertDialogBuilder(this)
                    .setNeutralButton(resources.getString(R.string.open_settings)) { _, _ ->

                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }
                    .show()
            }

            else -> {
                //Пользователь не разрешил использовать камеру первый раз → ничего не делаем
            }
        }

    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                intent?.apply {
                    textviewName.text = getStringExtra(TEXT_VIEW_NAME)
                    textviewSurname.text = getStringExtra(TEXT_VIEW_SUR_NAME)
                    textviewAge.text = getStringExtra(TEXT_VIEW_AGE)
                }
            }
        }

    companion object {
        const val TEXT_VIEW_NAME = "TEXT_VIEW_NAME"
        const val TEXT_VIEW_SUR_NAME = "TEXT_VIEW_SUR_NAME"
        const val TEXT_VIEW_AGE = "TEXT_VIEW_AGE"
    }
}