package com.stone.mvvm.database

import android.content.Context
import androidx.room.Room

object TVShowDatabaseProvider {
    private var database:TVShowDatabase?=null
    fun instance(context: Context):TVShowDatabase{
        if (database == null){
            database=Room.databaseBuilder(context,TVShowDatabase::class.java,"tvshow_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return database!!
    }

}