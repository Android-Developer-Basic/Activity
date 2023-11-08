package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

//
//  otus.gpb.homework.activities
//  Activities
//
//  Created by ponyu on 8.11.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

class ContractFillFromActivity : ActivityResultContract<String, Intent?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, FillFromActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        return intent
    }

}