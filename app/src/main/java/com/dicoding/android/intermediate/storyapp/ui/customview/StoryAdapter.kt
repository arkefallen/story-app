package com.dicoding.android.intermediate.storyapp.ui.customview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.databinding.StoryItemBinding
import com.dicoding.android.intermediate.storyapp.ui.DetailStoryActivity

class StoryAdapter(private val listStory : List<Story>) : RecyclerView.Adapter<StoryAdapter.ListStoryViewHolder>() {
    inner class ListStoryViewHolder(var binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder
        = ListStoryViewHolder(StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        holder.binding.itemUsernameChip.text = listStory[position].name
        holder.binding.tvItemDescription.text = listStory[position].description
        Glide.with(holder.itemView.context)
            .load(listStory[position].photoUrl)
            .into(holder.binding.ivImage)

        holder.binding.storyItem.setOnClickListener {
            val detailIntent = Intent(holder.itemView.context, DetailStoryActivity::class.java).apply {
                putExtra(DetailStoryActivity.EXTRA_USERNAME, listStory[position].name)
                putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, listStory[position].description)
                putExtra(DetailStoryActivity.EXTRA_IMAGE, listStory[position].photoUrl)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val optionsCompat : ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity
                )
            holder.itemView.context.startActivity(detailIntent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return listStory.size
    }
}