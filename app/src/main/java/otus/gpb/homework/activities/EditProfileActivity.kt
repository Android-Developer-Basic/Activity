package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editProfile: Button
    private lateinit var userFirstName: TextView
    private lateinit var userSurName: TextView
    private lateinit var userBirthday: TextView
    private var pictureUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        editProfile = findViewById(R.id.Edit_profile_button)
        userFirstName = findViewById(R.id.textview_name )
        userSurName = findViewById(R.id.textview_surname )
        userBirthday = findViewById(R.id.textview_age )


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
            val items = arrayOf("Сделать фото", "Выбрать фото")
            MaterialAlertDialogBuilder(this)
                .setTitle("Сделать/показать фото")
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> { setCameraPermission.launch(Manifest.permission.CAMERA) }
                        1 -> {
                            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                            showPicture.launch(pickImg)
                        }
                    }
                }
                .show()
        }

        editProfile.setOnClickListener(){
            val fillForm = Intent(this, FillFormActivity::class.java)
            fillFormAct.launch( fillForm )
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

        val textToSend = "${userFirstName.text} ${userSurName.text} ${userBirthday.text}"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setPackage("org.telegram.messenger")
            setType("image/*")
            if ( pictureUri != null) putExtra(Intent.EXTRA_STREAM, pictureUri )
            putExtra(Intent.EXTRA_TEXT, textToSend )
        }
        startActivity( shareIntent )

    }

    private val fillFormAct = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        result->
        if ( result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            userFirstName.text = data?.getStringExtra("firstname")
            userSurName.text = data?.getStringExtra("surname")
            userBirthday.text = data?.getStringExtra("birthday")
            val sendText = "${userFirstName.text} ${userSurName.text} ${userBirthday.text}"
            Log.d( "VPM_Log", sendText )

        }
    }


    private val setCameraPermission = registerForActivityResult( ActivityResultContracts.RequestPermission() ) {
        isPermit ->
        when (isPermit) {
            true -> findViewById<ImageView>( R.id.imageview_photo ).setImageResource( R.drawable.cat )
            false -> if ( shouldShowRequestPermissionRationale(Manifest.permission.CAMERA )) {
                        clarifyingDialog()
                     } else {  settingsDialog()  }
        }
    }

    private val showPicture = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        result->
        if ( result.resultCode == Activity.RESULT_OK) {
            val imgUri = result.data?.data
            if( imgUri != null ){
                pictureUri = imgUri
                populateImage( imgUri )
            }
        }
    }

    private fun clarifyingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Камера отключена")
            .setMessage("Необходимо настроить разрешение для камеры")
            .setPositiveButton("Настроить разрешение") { dialog, which ->
                setCameraPermission.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun settingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setCancelable( true )
            .setTitle("Камера отключена")
            .setMessage("Необходимо настроить разрешение для камеры")
            .setPositiveButton("Открыть настройки") { dialog, which ->
                startActivity( Intent(  Settings.ACTION_APPLICATION_DETAILS_SETTINGS, "package:$packageName".toUri() ))
            }
            .show()
    }
}