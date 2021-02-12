package com.stone.mvvm.models

import com.google.gson.annotations.SerializedName

data class TVShowDetails (
    @SerializedName("url") val url:String,
    @SerializedName("description") val description:String,
    @SerializedName("runtime") val runtime:String,
    @SerializedName("image_path") val image_path:String,
    @SerializedName("rating") val rating:String,
    @SerializedName("genres") val geners: Array<String> ,
    @SerializedName("pictures") val pictures:Array<String>,
    @SerializedName("episodes") val episodes:List<Episode>

    )