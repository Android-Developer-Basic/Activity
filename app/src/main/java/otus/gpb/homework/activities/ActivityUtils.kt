package otus.gpb.homework.activities

import android.app.Activity
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager



object ActivityUtils {

    fun getTaskAffinity(activity: Activity): String {
        val am = activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        am.appTasks.forEach { task ->

            val taskInfo = task.taskInfo

            val taskId = if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                taskInfo.id;
            } else {
                taskInfo.taskId;
            }

            if (activity.taskId == taskId) {
                val rootActivity = taskInfo.baseActivity
                return  runCatching {
                    activity
                        .packageManager
                        .getActivityInfo(rootActivity!!, PackageManager.GET_META_DATA)
                        .taskAffinity
                }. getOrNull() ?: ""

            }
        }
        return ""
    }
}