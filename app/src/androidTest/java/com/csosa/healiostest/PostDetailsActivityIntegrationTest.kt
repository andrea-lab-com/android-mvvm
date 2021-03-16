package com.csosa.healiostest

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.csosa.healiostest.activities.PostDetailActivity
import com.csosa.healiostest.commons.NavigationUtils
import com.csosa.healiostest.models.PostPresentation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class PostDetailsActivityIntegrationTest : BaseTest() {

    @get:Rule
    var activityRule: ActivityTestRule<PostDetailActivity> =
        ActivityTestRule(PostDetailActivity::class.java, false, false)

    @Test
    fun shouldDisplayErrorOnLaunchWithDefaultId() {
        val intent = Intent()
        activityRule.launchActivity(intent)
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(activityRule.activity.resources.getString(R.string.error_loading_post_details))))
    }

    @Test
    fun shouldLoadDataOnLaunchWithValidPostsId() {
        val intent = Intent().putExtra(
            NavigationUtils.POST_PARCEL_KEY,
            PostPresentation(
                id = 1,
                userId = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body= "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
            )
        )
        activityRule.launchActivity(intent)
        onView(withId(R.id.post_details_user_username_text_view)).check(matches(isDisplayed()))
    }

    @After
    override fun tearDown() {
        super.tearDown()
        activityRule.finishActivity()
    }

}
