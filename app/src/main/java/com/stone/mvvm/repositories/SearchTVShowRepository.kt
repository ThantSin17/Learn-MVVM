package com.stone.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stone.mvvm.network.ApiService
import com.stone.mvvm.network.RetrofitApi
import com.stone.mvvm.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTVShowRepository {
    private val apiService=RetrofitApi().getInstance().create(ApiService::class.java)
    private var dataList=MutableLiveData<TVShowResponse>()
    fun searchTVShow(query:String,page:Int):LiveData<TVShowResponse>{
        apiService.searchTVShow(query,page).enqueue(object :Callback<TVShowResponse>{
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        dataList.value=it
                    }
                }
               Log.i("SearchTVShowRepository","Success")
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
               Log.i("SearchTVShowRepository",t.message.toString())
            }

        })
        return dataList
    }
}