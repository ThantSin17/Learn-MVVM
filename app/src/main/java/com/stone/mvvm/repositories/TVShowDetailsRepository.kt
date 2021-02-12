package com.stone.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stone.mvvm.network.ApiService
import com.stone.mvvm.network.RetrofitApi
import com.stone.mvvm.responses.TVShowDetailsResponse
import com.stone.mvvm.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    private var apiService= RetrofitApi().getInstance().create(ApiService::class.java)
    private  var dataList= MutableLiveData<TVShowDetailsResponse>()
    fun getTVShowDetails(tvShowId:String):LiveData<TVShowDetailsResponse>{
        apiService.getTVShowDetails(tvShowId).enqueue(object :Callback<TVShowDetailsResponse>{
            override fun onResponse(
                call: Call<TVShowDetailsResponse>,
                response: Response<TVShowDetailsResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        dataList.value=it
                        Log.i("TVShowDetailsRepository","Success")
                    }
                }else{
                    Log.i("TVShowDetailsRepository","Fail")
                }
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                Log.i("TVShowDetailsRepository",t.message.toString())
            }

        })
        return dataList

    }

}