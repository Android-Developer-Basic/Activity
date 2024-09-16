package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonEdit : Button
    private lateinit var textName: TextView
    private lateinit var textSurname: TextView
    private lateinit var textAge: TextView


    private val selectPictureContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uriBitmap: Uri? ->
        uriBitmap?.let { populateImage(it) }
    }

    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
        if (permissionGranted) {
            // Есть доступ к камере, выводим картинку котенка
            imageView.setImageResource(R.drawable.cat)
        } else if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            // Выводим окно с переходом в настройки для изменения разрешений
            showToSetting()
        } else {
            // доступа к камере нет
        }
    }

    private val contractEditProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // заполним поля поученными данными
            val data = result.data
            val resultName = data!!.getStringExtra("name")
            val resultSurname = data.getStringExtra("surname")
            val resultAge = data.getStringExtra("age")
            textName.text = resultName
            textSurname.text = resultSurname
            textAge.text = resultAge

        } else {
            // нет изменений
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        buttonEdit = findViewById(R.id.buttonEditProfile)
        textName = findViewById(R.id.textview_name)
        textSurname = findViewById(R.id.textview_surname)
        textAge = findViewById(R.id.textview_age)

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

    override fun onStart() {
        super.onStart()
        imageView.setOnClickListener {
            showDialogSelectPhoto(this)
        }
        buttonEdit.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, FillFormActivity::class.java)
            contractEditProfile.launch(intent)
        }

    }

    private fun showDialogSelectPhoto(activity: Activity) {
        MaterialAlertDialogBuilder(activity)
            .setTitle("Выбор фото")
            .setMessage("Укажите как хотите получить фото?")
            .setNeutralButton("Сделать фото") { dialog, which ->
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    // есть доступ к камере
                    permissionCamera.launch(Manifest.permission.CAMERA)
                } else if(!shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA)) {
                    // нет доступа, заблокирован
                    permissionCamera.launch(Manifest.permission.CAMERA)
                } else {
                    // запрос с флагом don't ask again
                    showRationaleDialog(activity)
                }
            }
            .setPositiveButton("Выбрать фото") { dialog, which ->
                selectPictureContract.launch("image/*")
            }
            .show()
    }

    private fun showToSetting() {
        AlertDialog.Builder(this@EditProfileActivity)
            .setTitle("Нет доступа к камере")
            .setMessage("Необходимо разрешить доступ к камере в настройках приложения.")
            .setPositiveButton("Открыть настройки") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri =Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
            .create().show()
    }

    private fun showRationaleDialog(activity: Activity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Запрос доступа к камере")
            .setMessage("Необходимо получить доступ к камере чтобы была возможность делать фотографии. ")
            .setPositiveButton("Дать доступ") { dialog, which ->

                ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA), 0)
            }
            .setNegativeButton("Отмена") { dialog, which ->
                 dialog.dismiss()
            }
        builder.create().show()
    }


    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        //TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")

        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Log.d("Permission", "save to storage")
            requestPermissions(permissions,100)
        }


        val intent = Intent(Intent.ACTION_SEND).apply {
            setType("text/plain")
            //setPackage("org.telegram.messenger")
            val str = textName.text.toString() + " " + textSurname.text.toString() + ", " + textAge.text.toString()
            putExtra(Intent.EXTRA_TEXT, str)
        }

        // получим bitmap нашей картинки
        if (imageView.drawable != null) {
            val bitmap = imageView.drawToBitmap()
            // далее сохраним ее для передач ив intent

            val mPath = Environment.getExternalStorageDirectory().toString() + "/temp.jpeg"
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            // Uri image
            val uri = FileProvider.getUriForFile(this@EditProfileActivity,
                packageName+".provider", imageFile)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
        }

        intent.setPackage("org.telegram.messenger.web")
        //intent.setPackage("org.telegram.messenger")
        startActivity(intent)
    }
}