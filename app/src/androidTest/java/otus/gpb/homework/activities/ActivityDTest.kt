package otus.gpb.homework.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//  otus.gpb.homework.activities
//  Activities
//
//  Created by ponyu on 3.11.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

@RunWith(AndroidJUnit4::class)
class ActivityDTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ActivityD::class.java)

    @Test
    fun test_content() {
        onView(withId(R.id.rootLayout)).check(matches((isDisplayed())));
    }
}