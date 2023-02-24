package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class EditProfileActivity : AppCompatActivity() {

    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    //unnecessary user's PersonDTO object
    private val user = PersonDTO(null,null, null)

    private lateinit var imageView: ImageView

    //Uri of the image to be share with Telegram
    private var imageViewUri: Uri? = null

    //counter for the number of requests for camera permission
    private var cameraPermissionCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            createSingleChoiceDialog()
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                findViewById<TextView>(R.id.textview_name).text = result.data?.getStringExtra("name")
                findViewById<TextView>(R.id.textview_surname).text = result.data?.getStringExtra("lastName")
                findViewById<TextView>(R.id.textview_age).text = result.data?.getStringExtra("age")
                user.name = result.data?.getStringExtra("name")
                user.lastName = result.data?.getStringExtra("lastName")
                user.age = result.data?.getStringExtra("age")
            }
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

        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener {
            activityResultLauncher?.launch(Intent(this, FillFormActivity::class.java))
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri ->
            populateImage(imageUri)
            imageViewUri = imageUri
        }
    }

    private fun openSenderApp() {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageViewUri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "${user.name}\n${user.lastName}\n${user.age}")
        //type may be "*/*" too
        shareIntent.type = "image/*"
        shareIntent.setPackage("org.telegram.messenger")
        startActivity(shareIntent)
//("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if (!isGranted) {
            val dontShowAgain = !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            if (dontShowAgain) {
                cameraPermissionCounter++
                if(cameraPermissionCounter == 1) {
                    createSimpleDialog()
                    ++cameraPermissionCounter
                } else if (cameraPermissionCounter > 1)
                    despairDialog()
            }
        }
        else imageView.setImageResource(R.drawable.cat)
    }

    private fun createSingleChoiceDialog() {
        val checkedItem = intArrayOf(-1)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setSingleChoiceItems(arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo)), checkedItem[0]) { dialog, which ->
            checkedItem[0] = which
            for (i in checkedItem) {
                if (i == 0) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                        permissionRequestLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        imageView.setImageResource(R.drawable.cat)
                        // the element below returns counter to zero, (if it makes any sense at all)
//                        cameraPermissionCounter = 0
                    }
                } else takePictureLauncher.launch("image/*")

                dialog.dismiss()
            }
        }
        alertDialogBuilder.show()
    }

    private fun createSimpleDialog()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.to_permissions_title))
        builder.setMessage(getString(R.string.permission_explanation))

        builder.setNegativeButton(getString(R.string.cancel_dialog)){ dialog, i ->
            Toast.makeText(this, getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.allow_access)){ dialog, i ->
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                permissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        builder.show()
    }

    private fun despairDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setNeutralButton(getString(R.string.open_settings)) { dialog, i ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply{
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        builder.show()
    }
}