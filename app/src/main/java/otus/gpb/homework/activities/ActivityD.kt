package otus.gpb.homework.activities

import android.os.Bundle

class ActivityD : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)
        title = resources.getString(R.string.app_name) + " : " + localClassName + ", task=$taskId"

    }
}
