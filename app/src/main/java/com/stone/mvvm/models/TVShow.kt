package com.stone.mvvm.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "TV_SHOW")
data class TVShow(
    @PrimaryKey
    @SerializedName("id")  val id:Int,
    @SerializedName("name")  val name:String,
    @SerializedName("start_date")  val start_date:String,
    @SerializedName("network")  val network:String,
    @SerializedName("status")  val status:String,
    @SerializedName("country") val country:String,
    @SerializedName("image_thumbnail_path")  val image_thumbnail_path:String
) :Serializable