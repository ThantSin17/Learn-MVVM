package com.stone.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stone.mvvm.repositories.MostPopularTVShowRepository
import com.stone.mvvm.responses.TVShowResponse

class MostPopularTVShowViewModel : ViewModel() {
    private val mostPopularTVShowRepository=MostPopularTVShowRepository()
    fun getMostPopularTVShow(page:Int):LiveData<TVShowResponse>{
        return mostPopularTVShowRepository.getMostPopularTVShow(page)
    }
}