package com.stone.mvvm.listeners

import com.stone.mvvm.models.TVShow

interface WatchListListener {
    fun tvShowClick(tvShow: TVShow)
    fun removeImgClick(tvShow: TVShow)
}