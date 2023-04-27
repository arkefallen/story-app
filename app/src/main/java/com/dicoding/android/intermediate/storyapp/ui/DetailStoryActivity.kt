package com.dicoding.android.intermediate.storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.android.intermediate.storyapp.*

class DetailStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
    }

    companion object {
        val EXTRA_USERNAME = "extra_username"
        val EXTRA_IMAGE = "extra_image"
        val EXTRA_DESCRIPTION = "extra_description"
        val TAG = this::class.java.simpleName
    }
}