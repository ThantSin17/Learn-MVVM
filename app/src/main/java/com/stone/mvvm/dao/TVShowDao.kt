package com.stone.mvvm.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.stone.mvvm.models.TVShow
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface TVShowDao {
   @Query("SELECT * FROM tv_show")
   fun getAllTVShow():LiveData<List<TVShow>>

   @Query("select * from tv_show where id=:tvShowId")
   fun getTVShowFromWatchlist(tvShowId:Int):LiveData<TVShow>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun addToWatchList(tvShow: TVShow)

   @Update()
   fun updateTVShow(tvShow: TVShow)

   @Delete()
   fun removeFromWatchList(tvShow: TVShow)


}