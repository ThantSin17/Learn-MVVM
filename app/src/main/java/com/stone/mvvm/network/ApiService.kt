package com.stone.mvvm.network

import com.stone.mvvm.responses.TVShowDetailsResponse
import com.stone.mvvm.responses.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    fun getMostPopular(
        @Query("page") page: Int
    ): Call<TVShowResponse>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId:String):Call<TVShowDetailsResponse>

}