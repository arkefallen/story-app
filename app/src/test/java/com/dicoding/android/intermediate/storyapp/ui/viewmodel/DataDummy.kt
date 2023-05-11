package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import com.dicoding.android.intermediate.storyapp.data.response.Story

object DataDummy {
    fun generateDummyStoriesResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..12) {
            val quote = Story(
                "photoUrl ${i}",
                "createdAt ${i}",
                "name ${i}",
                "desc ${i}",
                "longitude ${i}",
                i.toString(),
                "latitude ${i}"
            )
            items.add(quote)
        }
        return items
    }
}