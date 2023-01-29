package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editButton: Button
    private var imgUri: Uri? = null


    private val resultContractFillEdit = registerForActivityResult(ContractFillEdit()){result ->
        findViewById<TextView>(R.id.textview_name).apply { text = result?.name }
        findViewById<TextView>(R.id.textview_surname).apply { text = result?.surname }
        findViewById<TextView>(R.id.textview_age).apply { text = result?.age }
    }


    private val callCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it)
            findViewById<ImageView>(R.id.imageview_photo).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
        else
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) goToSettings()
    }

    private val imageContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
            result ->
        if (result != null) {
            populateImage(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editButton = findViewById(R.id.btn_edit_profile)

        editButton.setOnClickListener { resultContractFillEdit.launch("")}



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

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.alert_dialog_title))
            .setNeutralButton(resources.getString(R.string.make_photo), DialogInterface.OnClickListener {
                    _, _ ->
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) rationaleDialog()
                else callCamera.launch(Manifest.permission.CAMERA)
            })
            .setPositiveButton(resources.getString(R.string.choose_photo), DialogInterface.OnClickListener { _, _ -> chooseImage() })

        imageView.setOnClickListener{
            dialog.show()
        }
    }

    private fun goToSettings() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.title_go_settings)
            .setPositiveButton(R.string.open_settings, DialogInterface.OnClickListener { _, _ ->
                startActivity( Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                })
            })
            .show()
    }

    private fun rationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.title_rationale_dialog))
            .setMessage(R.string.message_rationale_dialog)
            .setNegativeButton(R.string.Cancel, DialogInterface.OnClickListener { _, _ -> })
            .setPositiveButton(R.string.give_access, DialogInterface.OnClickListener { _, _ -> callCamera.launch(
                Manifest.permission.CAMERA) })
            .show()
    }

    private fun chooseImage() {
        imageContract.launch("image/*")
    }


    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imgUri = uri
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        try{
            val sendName = findViewById<TextView>(R.id.textview_name)
            val sendSurname = findViewById<TextView>(R.id.textview_surname)
            val sendAge = findViewById<TextView>(R.id.textview_age)
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, DTOuser(sendName.text.toString(), sendSurname.text.toString(), sendAge.text.toString()).toString())
                putExtra(Intent.EXTRA_STREAM, imgUri)
                type = "image/*"
                setPackage("org.telegram.messenger")
            }
            startActivity(Intent.createChooser(sendIntent, "Share"))

        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram не установлен", Toast.LENGTH_LONG).show()
        }
    }
}