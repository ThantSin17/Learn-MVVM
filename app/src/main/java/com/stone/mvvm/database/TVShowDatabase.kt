package com.stone.mvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stone.mvvm.dao.TVShowDao
import com.stone.mvvm.models.TVShow


@Database(entities = [TVShow::class],version = 1,exportSchema = false)
abstract class TVShowDatabase :RoomDatabase(){

    abstract fun tvShowDao():TVShowDao
}