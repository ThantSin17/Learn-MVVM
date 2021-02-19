package com.stone.mvvm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stone.mvvm.database.TVShowDatabaseProvider
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.repositories.TVShowDetailsRepository
import com.stone.mvvm.responses.TVShowDetailsResponse

class TVShowDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val tvShowDetailsRepository = TVShowDetailsRepository()
    private val database = TVShowDatabaseProvider.instance(application)


    fun getTVShowDetails(tvShowId: String)= tvShowDetailsRepository.getTVShowDetails(tvShowId)

    fun addToWatchList(tvShow: TVShow)=database.tvShowDao().addToWatchList(tvShow)
    fun getTVShowFromWatchList(tvShowId:Int)=database.tvShowDao().getTVShowFromWatchlist(tvShowId)
    fun removeFromWatchList(tvShow: TVShow)=database.tvShowDao().removeFromWatchList(tvShow)
}