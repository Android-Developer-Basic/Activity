package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.content.DialogInterface
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var repeatRequest : Boolean = false
    private lateinit var editProfileButton: Button

    private val launchPermissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // Permission granted
                    imageView.setImageResource(R.drawable.cat)
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    if( !repeatRequest ){
                        ViewDialogSettings()
                    }
                }
                else -> {
                    repeatRequest = true
                    // Permission denied
                }
            }
         }

    fun onButtonMakePhoto(dialog: DialogInterface?, which: Int) {
        val isGrantedCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if(isGrantedCamera){
            imageView.setImageResource(R.drawable.cat)
        }
        else{
            if( repeatRequest ){
                ViewDialogRepeat()
            }
            else{
                launchPermissionCamera.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    fun onButtonSelectPhoto(dialog: DialogInterface?, which: Int) {
        launchSelectPhoto.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
           )
        )
    }

    fun onButtonCancel(dialog: DialogInterface?, which: Int) {
        dialog?.dismiss()
    }

    fun onButtonGiveACCESS(dialog: DialogInterface?, which: Int) {
        launchPermissionCamera.launch(Manifest.permission.CAMERA)
        repeatRequest = false
    }

    fun onButtonOpenSettings(dialog: DialogInterface?, which: Int) {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        })
    }

    private fun ViewDialog()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setNeutralButton(resources.getString(R.string.cancel), ::onButtonCancel)
            .setPositiveButton(resources.getString(R.string.make_photo), ::onButtonMakePhoto)
            .setNegativeButton(resources.getString(R.string.select_photo), ::onButtonSelectPhoto)
            .show()
    }

    private fun ViewDialogRepeat()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.attention))
            .setMessage(resources.getString(R.string.message_text))
            .setPositiveButton(resources.getString(R.string.give_access), ::onButtonGiveACCESS)
            .setNegativeButton(resources.getString(R.string.cancel), ::onButtonCancel)
            .show()
    }

    private fun ViewDialogSettings()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setPositiveButton(resources.getString(R.string.open_settings), ::onButtonOpenSettings)
            .show()
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

        imageView.setOnClickListener {
            ViewDialog()
        }

        editProfileButton = findViewById(R.id.button4)
        editProfileButton.setOnClickListener {
           launchFillUserInfo.launch()
        }

    }

    private val launchSelectPhoto = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { result ->
        result?.let { populateImage(it) }
    }

    private val launchFillUserInfo = registerForActivityResult(ProfileContract()){ userInfo ->
        userInfo?.firstName?.let { findViewById<TextView>(R.id.textview_name).text = it }
        userInfo?.lastName?.let { findViewById<TextView>(R.id.textview_surname).text = it }
        userInfo?.age?.let { findViewById<TextView>(R.id.textview_age).text = it.toString() }
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageView.tag = uri
    }

    private fun openSenderApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            setPackage("org.telegram.messenger")

            val imgUri = imageView.tag as? Uri
            if (imgUri != null) putExtra(Intent.EXTRA_STREAM, imgUri)
            var firstName = findViewById<TextView>(R.id.textview_name).text
            var lastName = findViewById<TextView>(R.id.textview_surname).text
            var age = findViewById<TextView>(R.id.textview_age).text

            putExtra(Intent.EXTRA_TEXT,
                "Имя : ${firstName}\n" +
                      "Фамилия : ${lastName}\n" +
                      "Возраст : ${age}\n")
        }

        runCatching {
            startActivity(intent)
        }
    }

}