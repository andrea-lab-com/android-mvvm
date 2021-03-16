package com.csosa.healiostest.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.csosa.healiostest.R
import com.csosa.healiostest.adapters.createCommentsAdapter
import com.csosa.healiostest.base.BaseActivity
import com.csosa.healiostest.commons.*
import com.csosa.healiostest.databinding.ActivityPostDetailBinding
import com.csosa.healiostest.idlingresource.EspressoIdlingResource
import com.csosa.healiostest.models.CommentPresentation
import com.csosa.healiostest.models.PostPresentation
import com.csosa.healiostest.models.UserPresentation
import com.csosa.healiostest.viewmodel.PostDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PostDetailActivity : BaseActivity(), IPostDetailBinder {

    // region Members

    private val postDetailViewModel by viewModel<PostDetailViewModel>()

    private lateinit var binding: ActivityPostDetailBinding

    private val commentsAdapter = createCommentsAdapter()

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postPresentation =
            intent.getParcelableExtra<PostPresentation>(NavigationUtils.POST_PARCEL_KEY)

        observeDetailViewState()

        if (postPresentation == null) {
            postDetailViewModel
                .displayPostError(R.string.error_loading_post_details)
        } else {

            observeNetworkChanges(postPresentation)
            postDetailViewModel.getPostDetails(postPresentation)
        }

    }

    // endregion

    // region Private API

    private fun observeDetailViewState() {

        postDetailViewModel.detailViewState.observe(this, Observer {
            bindPostDetail(it.info)
            bindUser(it.user)
            bindComments(it.comments)
            it.error?.let { e ->
                onError(resources.getString(e.message))
            }
            if (it.isComplete) {
                EspressoIdlingResource.decrement()
                showSnackBar(
                    binding.postDetailsLayout,
                    getString(R.string.info_loading_complete)
                )
            }
        })
    }

    private fun onError(message: String) {
        EspressoIdlingResource.decrement()
        binding.commentsLayout.commentsProgressBar.hide()
        binding.userLayout.userProgressBar.hide()
        binding.commentsLayout.commentsErrorTextView.show()
        binding.userLayout.userErrorTextView.show()
        showSnackBar(binding.postDetailsLayout, message, isError = true)
    }

    private fun onErrorResolved() {

        binding.infoLayout.postDetailsTitleTitleTextView.remove()
        binding.infoLayout.postDetailsBodyTextView.remove()
        binding.userLayout.postDetailsUserUsernameTextView.remove()
        binding.userLayout.postDetailsUserNameTextView.remove()
        binding.userLayout.postDetailsUserEmailTextView.remove()
        binding.commentsLayout.postDetailsCommentsRecyclerView.remove()
        binding.userLayout.userProgressBar.show()
        binding.commentsLayout.commentsProgressBar.show()
    }

    private fun observeNetworkChanges(post: PostPresentation) {
        onNetworkChange { isConnected ->
            postDetailViewModel.detailViewState.value?.let { viewState ->
                if (isConnected && viewState.error != null) {
                    onErrorResolved()
                    postDetailViewModel.getPostDetails(post, isRetry = true)
                }
            }
        }
    }

    // endregion

    // region IPostDetailsBinder

    override fun bindPostDetail(post: PostPresentation?) {
        supportActionBar?.title = getString(R.string.title_detail_post)
        binding.infoLayout.post = post
    }

    override fun bindComments(comments: List<CommentPresentation>?) {
        comments?.let {
            with(binding.commentsLayout) {
                commentsProgressBar.remove()
                if (comments.isNotEmpty()) {
                    postDetailsCommentsRecyclerView.apply {
                        adapter = commentsAdapter.apply { submitList(comments) }

                        EspressoIdlingResource.decrement()
                    }
                    postDetailsCommentsRecyclerView.show()
                    commentsErrorTextView.hide()
                } else {
                    postDetailsCommentsRecyclerView.hide()
                    commentsErrorTextView.show()
                }
            }
        }
    }

    override fun bindUser(user: UserPresentation?) {
        user?.let {
            with(binding.userLayout) {
                userProgressBar.hide()
                this.user = user
                postDetailsUserUsernameTextView.show()
                postDetailsUserNameTextView.show()
                postDetailsUserEmailTextView.show()
            }
        }
    }

    // endregion

}
