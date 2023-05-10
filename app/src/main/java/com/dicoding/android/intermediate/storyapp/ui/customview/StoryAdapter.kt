package com.dicoding.android.intermediate.storyapp.ui.customview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.databinding.StoryItemBinding
import com.dicoding.android.intermediate.storyapp.ui.DetailStoryActivity

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.ListStoryViewHolder>(DIFF_CALLBACK) {
    inner class ListStoryViewHolder(private val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Story) {
            binding.itemUsernameChip.text = data.name
            binding.tvItemDescription.text = data.description
            Glide.with(binding.root.context)
                .load(data.photoUrl)
                .into(binding.ivImage)
            binding.storyItem.setOnClickListener {
                val detailStoryIntent = Intent(binding.root.context, DetailStoryActivity::class.java).apply {
                    this.putExtra(DetailStoryActivity.EXTRA_USERNAME, data.name)
                    this.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, data.description)
                    this.putExtra(DetailStoryActivity.EXTRA_IMAGE, data.photoUrl)
                }
                val optionsCompat : ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity
                    )
                binding.root.context.startActivity(detailStoryIntent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data!=null) {
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
