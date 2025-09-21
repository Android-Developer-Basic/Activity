package otus.gpb.homework.alertsdialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

interface EditProfileListener {
    fun callCameraPermissionLauncher()
    fun openGallery()
}

class EditProfileDialog(private val listener: EditProfileListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Выберите действие")
                .setNegativeButton("Отменить") { _, _ ->
                }
                .setItems(arrayOf("Сделать фото", "Выбрать фото")) { dialog, which ->
                    when (which) {
                        0 -> {listener.callCameraPermissionLauncher()}
                        1 -> {listener.openGallery()}
                    }
                }
            builder.create()
        } ?: throw IllegalStateException()
    }
}