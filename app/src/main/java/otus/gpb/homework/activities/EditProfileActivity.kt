package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.DialogInterface
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EditProfileActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE: Int = 200
    private val REQUEST_CAMERA_STATE: Int = 0
    private lateinit var imageView: ImageView
    private lateinit var editProfileBtn: Button
    private lateinit var pickedImageUri: Uri
    private var user = UserData("","","")

    private val resultContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        if(!granted)
        {
            if(!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
            {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Запрос разрешения")
                    .setMessage("Необходимо разрешение на использование камеры. Для этого перейдите в настройки.")
                    .setPositiveButton("Открыть настройки") { _, _ ->
                        val intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                val uri = Uri.fromParts("package", packageName, null)
                                data = uri
                            }
                        startActivity(intent)
                    }
                    .show()
            }
        }
        else
        {
            pickedImageUri = Uri.parse("android.resource://otus.gpb.homework.activities/" + R.drawable.cat)
            populateImage(pickedImageUri)
        }
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            pickedImageUri = uri
            populateImage(uri)
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private val fillFromActivity = registerForActivityResult(ProfileInfoContract()){ result->
        findViewById<TextView>(R.id.textview_name).text = result?.getString("name")
        findViewById<TextView>(R.id.textview_surname).text = result?.getString("surname")
        findViewById<TextView>(R.id.textview_age).text = result?.getString("age")
        user.name = result?.getString("name")
        user.surname = result?.getString("surname")
        user.age = result?.getString("age")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editProfileBtn = findViewById(R.id.button4)

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
            val listItems = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setTitle("Выберите действие")
                .setItems(listItems,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        when (i) {
                            0->resultContract.launch(Manifest.permission.CAMERA)
                            1->pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            else->{
                                Toast.makeText(this, "No action", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                .setNegativeButton(android.R.string.cancel) { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // Respond to positive button press
                    resultContract.launch(Manifest.permission.CAMERA)
                }
                .show()
        }

        editProfileBtn.setOnClickListener {
            fillFromActivity.launch("")
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
        //TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")

        try {
            val telegram = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, pickedImageUri)
                putExtra(Intent.EXTRA_TEXT, "${user.name}\n${user.surname}\n${user.age}")
                type = "image/*"
                setPackage("org.telegram.messenger")
            }

            startActivity(telegram)
            }
            catch (e :ActivityNotFoundException) {
                Toast.makeText(this, "Telegram app is not installed", Toast.LENGTH_LONG).show()
            }
            catch (e: Exception) {
                Toast.makeText(this, "Не указаны необходимые данные", Toast.LENGTH_LONG).show()
            }
    }

    private fun showRationaleDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.rationale_title))
            .setMessage(getString(R.string.rationale_desc))
            .setNegativeButton("Отмена"){dialog, which->
            }
            .setPositiveButton("Дать доступ") { dialog, which ->
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_STATE)
            }
        builder.create().show()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ((grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            showRationaleDialog()
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}