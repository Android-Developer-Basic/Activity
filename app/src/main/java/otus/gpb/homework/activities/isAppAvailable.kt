package otus.gpb.homework.activities

import android.content.Context
import android.content.pm.PackageManager

fun isAppAvailable(context: Context, appName: String): Boolean {
  val pm: PackageManager = context.packageManager

  return try {
    pm.getPackageInfo(appName, 0)
    true
  }
  catch (e: Throwable) {
    println("EEEEEEE e $e")
    false
  }
}