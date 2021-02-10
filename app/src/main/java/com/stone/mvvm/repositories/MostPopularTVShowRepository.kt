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

class MostPopularTVShowRepository {
    private var apiService= RetrofitApi().getInstance().create(ApiService::class.java)
    private  var dataList=MutableLiveData<TVShowResponse>()
    fun getMostPopularTVShow( page:Int):LiveData<TVShowResponse>{
       apiService.getMostPopular(page).enqueue(object :Callback<TVShowResponse>{
           override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
               if (response.isSuccessful){
                   response.body().let {
                       dataList.value=it
                       Log.i("MostPopularTVShowRepository","success")
                   }
               }else{
                   Log.i("MostPopularTVShowRepository","fail")
               }
           }

           override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
               dataList.value=null
               Log.i("MostPopularTVShowRepository",t.message.toString())
           }

       })
        return dataList
    }
}