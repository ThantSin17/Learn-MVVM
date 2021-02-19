package com.stone.mvvm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stone.mvvm.database.TVShowDatabaseProvider
import com.stone.mvvm.repositories.SearchTVShowRepository
import com.stone.mvvm.responses.TVShowResponse

class SearchTVShowViewModel(application: Application) :AndroidViewModel(application) {
   private val searchTVShowRepository=SearchTVShowRepository()
    fun searchTVShow(query:String,page:Int):LiveData<TVShowResponse>{
      return searchTVShowRepository.searchTVShow(query,page)
    }
}