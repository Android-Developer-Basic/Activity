package otus.gpb.homework.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.Toast
import android.Manifest
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import android.content.ActivityNotFoundException

class EditProfileActivity() : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener {
        val eIntent = Intent(applicationContext, SecondActivity::class.java)
        eIntent.putExtra("first_name", findViewById<TextView>(R.id.textview_name).text)// - имя
        eIntent.putExtra("last_name"   , findViewById<TextView>(R.id.textview_surname).text)// - фамилия
        eIntent.putExtra("age",  findViewById<TextView>(R.id.textview_age).text)// - возраст
        launcher.launch(eIntent)
       }
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
           val items = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(it.context)
                .setTitle("Выберите действие")
                .setItems(items) { dialog, which ->
                       when(which) {
                        0 ->  if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            MaterialAlertDialogBuilder(this)
                                .setTitle("Инфо")
                                .setIcon(android.R.drawable.ic_menu_camera)
                                .setMessage("Чтобы сделать фото нужно предоставить доступ к камере")
                                .setPositiveButton("Дать доступ") { dialog, _ ->
                                    permissionCamera.launch(Manifest.permission.CAMERA)
                                }
                                .setNegativeButton("Отмена") { dialog, _ ->
                                    dialog.cancel()
                                }
                                .show()
                        } else {
                            permissionCamera.launch(Manifest.permission.CAMERA)
                        }
                           1 ->  resultImageContent.launch("image/*")
                        else -> return@setItems
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
    private val resultImageContent = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageUri = it
        it?.let { populateImage(it) }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
          result -> result ?: return@registerForActivityResult

        if(result.resultCode == Activity.RESULT_OK) {
            result.data?.extras?.let { extras ->
                var text = extras.getString("first_name").toString()
                findViewById<TextView>(R.id.textview_name).text = text
                text = extras.getString("last_name").toString()
                findViewById<TextView>(R.id.textview_surname).text = text
                text = extras.getString("age").toString()
                findViewById<TextView>(R.id.textview_age).text = text
            }
        }
    }
    val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        findViewById<ImageView>(R.id.imageview_photo).setImageBitmap(bitmap)
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        when {
            granted -> {
                //Пермишен к камере получен, запускаем камеру
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)
                imageUri = Uri.parse("android.resource://$packageName/${R.drawable.cat}")
                imageView.setImageBitmap(bitmap)
                camera.launch(null)
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {

                MaterialAlertDialogBuilder(this)
                    .setTitle("Инфо")
                    .setMessage("Доступ к камере запрещен")
                    .setNeutralButton("Открыть настройки") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            val uri = Uri.fromParts("package", packageName, null)
                            data = uri
                        }
                        startActivity(intent)
                    }
                   .show()

            }
            else -> {
                Toast.makeText(this, "Отмена", Toast.LENGTH_SHORT).show()
            }
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
        val first_name = findViewById<TextView>(R.id.textview_name).text
        val last_name = findViewById<TextView>(R.id.textview_surname).text
        val age = findViewById<TextView>(R.id.textview_age).text
        try {
             val intent = Intent(Intent.ACTION_SEND)
            intent.setPackage("org.telegram.messenger")
            intent.putExtra(Intent.EXTRA_TEXT, "${first_name}\n${last_name}\n${age}")
            intent.setType("image/*")
            if (imageUri != null) {
               intent.putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            startActivity(Intent.createChooser(intent, "Отправить:"));
        }
        catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram не установлен", Toast.LENGTH_SHORT).show()
        }
    }
    }
