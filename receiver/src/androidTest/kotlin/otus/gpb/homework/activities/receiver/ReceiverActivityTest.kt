package otus.gpb.homework.activities.receiver

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//  otus.gpb.homework.activities.receiver
//  Activities
//
//  Created by ponyu on 5.11.2023
//  Copyright © 2023 ponyu. All rights reserved.
//

@RunWith(AndroidJUnit4::class)
class ReceiverActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ReceiverActivity::class.java)

    /*
    * TODO Не знаю почему но этот тест проходит с ошибкой.
    *  Я поищу решение этой проблемы позже.
    *  Ошибка такая:
    *  java.lang.IncompatibleClassChangeError: Class 'org.hamcrest.StringDescription' does not implement interface 'java.lang.Iterable' in call to 'java.util.Iterator java.lang.Iterable.iterator()'
    *  (declaration of 'org. hamcrest.BaseDescription' appears in /data/app/~~-lvl1h8viM56bJ93fq2CCA==/otus.gpb.homework.activities.receiver.test-AWX1oHEHZM_sA3s_bIx2Xw==/base.apk)*/
    @Test
    fun test_content() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.posterImageView),
                ViewMatchers.isAssignableFrom(ImageView::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(ConstraintLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.titleTextView),
                ViewMatchers.withText(""),
                ViewMatchers.isAssignableFrom(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(ConstraintLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.descriptionTextView),
                ViewMatchers.withText(""),
                ViewMatchers.isAssignableFrom(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(ConstraintLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.yearTextView),
                ViewMatchers.withText(""),
                ViewMatchers.isAssignableFrom(TextView::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(ConstraintLayout::class.java)),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.rootLayout)),
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}