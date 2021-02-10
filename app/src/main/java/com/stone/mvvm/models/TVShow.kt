package com.stone.mvvm.models


import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("id")  val id:Int,
    @SerializedName("name")  val name:String,
    @SerializedName("start_date")  val start_date:String,
    @SerializedName("network")  val network:String,
    @SerializedName("status")  val status:String,
    @SerializedName("country") val country:String,
    @SerializedName("image_thumbnail_path")  val image_thumbnail_path:String
)