package com.stone.mvvm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stone.mvvm.database.TVShowDatabaseProvider
import com.stone.mvvm.models.TVShow

class WatchlistViewModel(application: Application) :AndroidViewModel(application) {
    private val database=TVShowDatabaseProvider.instance(application)
    fun getAllWatchList(): LiveData<List<TVShow>> =  database.tvShowDao().getAllTVShow()
    fun removeFromWatchList(tvShow: TVShow)=database.tvShowDao().removeFromWatchList(tvShow)


}