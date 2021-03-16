package com.csosa.healiostest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.csosa.healiostest.activities.ListPostsActivity
import com.csosa.healiostest.adapters.PostViewHolder
import com.csosa.healiostest.helpers.ViewAction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class ListPostActivityIntegrationTest : BaseTest() {

    @get:Rule
    var activityRule: ActivityTestRule<ListPostsActivity> =
        ActivityTestRule(ListPostsActivity::class.java, false, false)

    @get:Rule
    val intentsTestRule = IntentsTestRule(ListPostsActivity::class.java)

    @Test
    fun shouldNavigateToPostDetailOnItemClickFromList() {
        onView(withId(R.id.posts_recycler_view)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        onView(withId(R.id.posts_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(
                0, ViewAction.clickChildViewWithId(R.id.post_show_detail_button)
            )
        )
        intended(hasComponent(POST_DETAIL_ACTIVITY_COMPONENT))
    }

    companion object {
        private const val POST_DETAIL_ACTIVITY_COMPONENT =
            "com.csosa.healiostest.activities.PostDetailActivity"
    }

}
