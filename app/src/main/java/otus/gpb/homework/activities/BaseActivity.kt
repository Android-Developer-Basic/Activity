package otus.gpb.homework.activities

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

const val INFO_TAG = "ActivitiesInfo"

open class BaseActivity : AppCompatActivity() {
    private companion object {
        var nextId = 1
    }

    private lateinit var activityManager: ActivityManager
    private var id: Int = 0
    private var nameWithId : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = nextId++
        activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        nameWithId = "${localClassName}(id:${id})"
        title = nameWithId
        printInfoAboutCalledMethod("onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        printInfoAboutCalledMethod("onNewIntent")
    }

    override fun onResume() {
        super.onResume()
        printInfoAboutCalledMethod("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        printInfoAboutCalledMethod("onDestroy")
    }

    private fun printInfoAboutCalledMethod(methodName: String) {
        Log.i(INFO_TAG, "\n")
        Log.i(INFO_TAG, "${methodName} was called for ${nameWithId}")
        printTaskInfo()
    }

    private fun printTaskInfo() {
        for (task in activityManager.appTasks) {
            Log.i(INFO_TAG, "Task info:")
            Log.i(INFO_TAG, "\tCount: ${task.taskInfo.numActivities}")
            Log.i(INFO_TAG, "\tTop: ${task.taskInfo.topActivity?.shortClassName}")
            Log.i(INFO_TAG, "\tRoot: ${task.taskInfo.baseActivity?.shortClassName}")
        }
    }
}