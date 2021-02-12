package com.stone.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stone.mvvm.repositories.TVShowDetailsRepository
import com.stone.mvvm.responses.TVShowDetailsResponse

class TVShowDetailsViewModel :ViewModel() {
    private val tvShowDetailsRepository=TVShowDetailsRepository()
    fun getTVShowDetails(tvShowId:String):LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }
}