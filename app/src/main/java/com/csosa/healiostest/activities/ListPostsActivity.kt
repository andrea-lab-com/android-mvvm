package com.csosa.healiostest.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.csosa.healiostest.R
import com.csosa.healiostest.adapters.createPostsAdapter
import com.csosa.healiostest.base.BaseActivity
import com.csosa.healiostest.commons.*
import com.csosa.healiostest.databinding.ActivityPostsBinding
import com.csosa.healiostest.idlingresource.EspressoIdlingResource
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.states.ListPostsViewState
import com.csosa.healiostest.viewmodel.ListPostsViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ListPostsActivity : BaseActivity() {

    // region Members

    private val listPostsViewModel by viewModel<ListPostsViewModel>()

    private lateinit var binding: ActivityPostsBinding

    private val postsAdapter = createPostsAdapter {
        startActivity<PostDetailActivity> {
            putExtra(NavigationUtils.POST_PARCEL_KEY, it)
        }
    }

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts)
        configSupportActionBar()

        observePostsViewState()
    }

    override fun onResume() {
        super.onResume()
        listPostsViewModel.executeGetAllPosts()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            startActivity<SettingsActivity>()
            true
        } else super.onOptionsItemSelected(item)
    }

    // endregion

    // region Private API

    private fun configSupportActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.title_list_post)
    }

    private fun observePostsViewState() {
        listPostsViewModel.postsViewState.observe(this, Observer { state ->

            handlePostsLoading(state)

            state.posts?.let { posts ->
                if (posts.isNotEmpty()) {
                    handlePosts(posts)
                } else {
                    binding.noPostsTextView.show()
                }
            }

            handlePostsError(state)
        })
    }

    private fun handlePostsLoading(state: ListPostsViewState) {

        if (state.isLoading) {
            binding.postsProgressBar.show()
        } else {
            binding.postsProgressBar.hide()
        }
    }

    private fun handlePosts(posts: List<PostPresentation>) {
        binding.noPostsTextView.hide()
        binding.postsRecyclerView.show()
        binding.postsRecyclerView.apply {
            adapter = ScaleInAnimationAdapter(postsAdapter.apply {
                submitList(posts)
                EspressoIdlingResource.decrement()
            })
        }
    }

    private fun handlePostsError(state: ListPostsViewState) {
        EspressoIdlingResource.decrement()
        state.error?.run {
            showSnackBar(
                binding.postsRecyclerView,
                getString(this.message),
                isError = true
            )
        }
    }

    // endregion
}
