package com.dicoding.android.intermediate.storyapp.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserStoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<Story>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
@Entity(tableName = "story")
data class Story(

	@ColumnInfo("photoUrl")
	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@ColumnInfo("createdAt")
	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@ColumnInfo("name")
	@field:SerializedName("name")
	val name: String? = null,

	@ColumnInfo("description")
	@field:SerializedName("description")
	val description: String? = null,

	@ColumnInfo("lon")
	@field:SerializedName("lon")
	val lon: String? = null,

	@PrimaryKey(false)
	@ColumnInfo("id")
	@field:SerializedName("id")
	val id: String,

	@ColumnInfo("lat")
	@field:SerializedName("lat")
	val lat: String? = null

) : Parcelable
