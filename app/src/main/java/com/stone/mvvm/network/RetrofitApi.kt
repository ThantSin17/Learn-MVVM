package com.stone.mvvm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {
    private var retrofit:Retrofit? = null
    fun getInstance() : Retrofit{
        if (retrofit==null){
            retrofit=Retrofit.Builder()
                .baseUrl("https://www.episodate.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}