package com.dicoding.android.intermediate.storyapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.intermediate.storyapp.databinding.ActivityStoriesBinding
import com.dicoding.android.intermediate.storyapp.repo.UserPreferences
import com.dicoding.android.intermediate.storyapp.ui.customview.LoadingStateAdapter
import com.dicoding.android.intermediate.storyapp.ui.customview.StoryAdapter
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModelFactory
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.StoryViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.StoryViewModelFactory

class StoriesActivity : AppCompatActivity() {
    private lateinit var storiesBinding: ActivityStoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storiesBinding = ActivityStoriesBinding.inflate(layoutInflater)
        setContentView(storiesBinding.root)

        supportActionBar?.hide()

        val authViewModel : AuthViewModel by viewModels { AuthViewModelFactory.getInstance() }
        val preferences = UserPreferences.getInstance(dataStore)
        authViewModel.setPreferences(preferences)

        authViewModel.getUserLoginName()?.observe(
            this, {
                if (it.isEmpty() || it == "" || it == null) {
                    storiesBinding.tvAccountName.visibility = View.GONE
                } else {
                    storiesBinding.tvAccountName.visibility = View.VISIBLE
                    storiesBinding.tvAccountName.text = it.toString()
                }
            }
        )

        authViewModel.getUserLoginToken()?.observe(
            this, {
                if (it.isEmpty() || it == "" || it == null) {
                    storiesBinding.apply {
                        this.settings.visibility = View.GONE
                        this.tvWelcome.visibility = View.GONE
                        this.rvStory.visibility = View.GONE
                        this.loading.visibility = View.VISIBLE
                    }
                } else {
                    storiesBinding.apply {
                        this.settings.visibility = View.VISIBLE
                        this.tvWelcome.visibility = View.VISIBLE
                        this.loading.visibility = View.GONE
                        this.rvStory.visibility = View.VISIBLE
                        this.rvStory.layoutManager = LinearLayoutManager(this@StoriesActivity)
                    }

                    val fabInsertStory = storiesBinding.fabInsertStory
                    fabInsertStory.setOnClickListener { _ ->
                        val postStoryIntent = Intent(this, PostStoryActivity::class.java).apply {
                            this.putExtra(PostStoryActivity.EXTRA_USER_TOKEN, it)
                        }
                        startActivity(postStoryIntent)
                    }

                    val storyViewModel : StoryViewModel by viewModels { StoryViewModelFactory.getInstance(it, this@StoriesActivity) }

                    storyViewModel.getStories().observe(
                        this, {
                            if (it != null) {
                                val adapter = StoryAdapter()
                                storiesBinding.rvStory.adapter = adapter.withLoadStateFooter(
                                    footer = LoadingStateAdapter {
                                        adapter.retry()
                                    }
                                )
                                storiesBinding.rvStory.setHasFixedSize(true)
                                adapter.submitData(lifecycle, it)
                            }
                        }
                    )
                }
            }
        )

        val settings = storiesBinding.settings
        settings.setOnClickListener {
            val settingsIntent = Intent(this@StoriesActivity, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        val EXTRA_USER_NAME = "extra_user_name"
    }
}