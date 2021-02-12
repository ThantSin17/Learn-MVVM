package com.stone.mvvm.responses

import com.google.gson.annotations.SerializedName
import com.stone.mvvm.models.TVShowDetails

data class TVShowDetailsResponse (
    @SerializedName("tvShow")
    val tvShowDetails:TVShowDetails
)