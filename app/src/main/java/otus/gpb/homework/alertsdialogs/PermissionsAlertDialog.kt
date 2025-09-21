package otus.gpb.homework.alertsdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

interface PermissionDialogListener {
    fun getCameraPermissionLauncher()
}

class PermissionsAlertDialog(private val listener: PermissionDialogListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage("Приложению нужен доступ к камере, чтобы сделать фото")
                .setTitle("Разрешения камеры")
                .setPositiveButton("Дать доступ") { _, _ ->
                    listener.getCameraPermissionLauncher()
                }
                .setNegativeButton("Отмена") { _, _ ->

                }
            builder.create()
        } ?: throw IllegalStateException()
    }
}