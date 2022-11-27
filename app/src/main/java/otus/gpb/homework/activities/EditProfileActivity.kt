package otus.gpb.homework.activities

import MyActivityContract
import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class EditProfileActivity : AppCompatActivity() {

    val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        granted ->
        if (!granted)
        {
            val dontShowAgain = !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            if (dontShowAgain)
            {
                AlertDialog.Builder(this@EditProfileActivity)
                    .setMessage("Надо")
                    .setPositiveButton("Дать доступ") { dialogInterface, i ->
                        //ActivityResultContracts.RequestPermission().launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("Отмена") { dialogInterface, i ->
                        dialogInterface.cancel()
                        AlertDialog.Builder(this@EditProfileActivity)
                            .setMessage("Открыть настройки?")
                            .setPositiveButton("Открыть") { dialogInterface, i ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    val uri = Uri.fromParts("package", packageName, null)
                                    data = uri
                                }
                                startActivity(intent)
                            }
                            .show()
                    }
                    .show()
            }
        }
        else
        {
                val uri = Uri.parse("android.resource://" + this@EditProfileActivity.getPackageName().toString() + "/drawable/cat")
                populateImage(uri)
        }
    }
    var saveUri = ""
    val resultContract = registerForActivityResult(ActivityResultContracts.GetContent()){result ->
        populateImage(Uri.parse(result.toString()))
        saveUri = result.toString()
    }
    val fillFormActivityContract = registerForActivityResult(MyActivityContract()){result ->
        findViewById<TextView>(R.id.textview_name).text = result?.name.toString()
        findViewById<TextView>(R.id.textview_surname).text = result?.lastName.toString()
        findViewById<TextView>(R.id.textview_age).text = result?.age.toString()
    }
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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
        imageView.setOnClickListener()
        {

            AlertDialog.Builder(this@EditProfileActivity)
                .setMessage("Выберите действие")
                .setPositiveButton("Сделать фото") { dialogInterface, i ->
                    singlePermission.launch(Manifest.permission.CAMERA)
                }
                .setNegativeButton("Выбрать фото") { dialogInterface, i ->
                   resultContract.launch("image/*")
                }
                .show()
        }

        findViewById<Button>(R.id.button4).setOnClickListener()
        {
            fillFormActivityContract.launch(0)
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
        var uri: Uri?
        if(saveUri != "")
        {
            uri = Uri.parse(saveUri)
        }
        else
        {
            uri = Uri.parse("android.resource://$packageName/${R.drawable.cat}")
        }
        val userData: ArrayList<String> = arrayListOf(
            findViewById<TextView>(R.id.textview_surname).text.toString(),
            findViewById<TextView>(R.id.textview_name).text.toString(),
            findViewById<TextView>(R.id.textview_age).text.toString(),
            )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setPackage("org.telegram.messenger")
            type = "image/*"

            putExtra(Intent.EXTRA_TEXT, userData.toString())
            uri?.let {
                putExtra(Intent.EXTRA_STREAM, it)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        }
        startActivity(shareIntent)

    }
        //TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")

}