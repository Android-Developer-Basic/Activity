package otus.gpb.homework.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val resultContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        if(!granted)
        {
            val dontShowAgain = !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

            if(dontShowAgain)
            {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
        }
        else
        {
            //TODO
        }
    }

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

        imageView.setOnClickListener{

            var MemberList = ArrayList<String>()
            MemberList.add("tahir")
            MemberList.add("usman")
            MemberList.add("waqas")

            val items = arrayOf<CharSequence>("Foo", "Bar", "Baz")
            val checkedItem = intArrayOf(-1)
            val listItems = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems (listItems, checkedItem[0], DialogInterface.OnClickListener { dialogInterface, i -> })
                .setTitle("title")

//                .setItems(items,
//                    DialogInterface.OnClickListener { dialogInterface, i -> })
                .setNeutralButton(android.R.string.cancel) { dialog, which ->
                    // Respond to neutral button press
                }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // Respond to positive button press
                    resultContract.launch(Manifest.permission.CAMERA)
                    //Toast.makeText(applicationContext, listItems[checkedItem[which]] + " is clicked", Toast.LENGTH_SHORT).show()
                }
                .show()
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
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}