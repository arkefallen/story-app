package com.dicoding.android.intermediate.storyapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.android.intermediate.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var detailStoryBinding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailStoryBinding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(detailStoryBinding.root)

        supportActionBar?.hide()

        val image = intent.getStringExtra(EXTRA_IMAGE)
        val name = intent.getStringExtra(EXTRA_USERNAME)
        val desc = intent.getStringExtra(EXTRA_DESCRIPTION)

        Glide.with(this)
            .load(image)
            .into(detailStoryBinding.ivImageDetail)

        detailStoryBinding.apply {
            this.tvUserDetail.text = name
            this.tvDescDetail.text = desc
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        val EXTRA_USERNAME = "extra_username"
        val EXTRA_IMAGE = "extra_image"
        val EXTRA_DESCRIPTION = "extra_description"
        val TAG = this::class.java.simpleName
    }
}