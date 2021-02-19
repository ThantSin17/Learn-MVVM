package com.stone.mvvm.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.stone.mvvm.R
import com.stone.mvvm.adapters.WatchListAdapter
import com.stone.mvvm.databinding.ActivityWatchlistBinding
import com.stone.mvvm.listeners.WatchListListener
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.WatchlistViewModel

class WatchlistActivity : AppCompatActivity(), WatchListListener {
    private lateinit var binding: ActivityWatchlistBinding

    private val viewModel by viewModels<WatchlistViewModel>()
    private val adapter=WatchListAdapter(this)

    companion object {
        fun gotoWatchList(context: Context): Intent {
            return Intent(context, WatchlistActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        toggleLoading()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist)
        // setContentView(R.layout.activity_watchlist)


        binding.watchlistRecycler.setHasFixedSize(true)
        binding.watchlistRecycler.adapter=adapter
        viewModel.getAllWatchList().observe(this, {
            adapter.submitList(it)
            toggleLoading()
            binding.watchlistRecycler.visibility= View.VISIBLE
            Toast.makeText(this,"observe",Toast.LENGTH_SHORT).show()

        })

        doOnClick()
    }

    private fun doOnClick() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun toggleLoading() {
        when (binding.isLoading) {
            null -> binding.isLoading = true
            false -> binding.isLoading = true
            true -> binding.isLoading = false
        }
    }

    override fun tvShowClick(tvShow: TVShow) {
        startActivity(TVShowDetailActivity.gotoTVShowActivity(this,tvShow))
    }

    override fun removeImgClick(tvShow: TVShow) {
        toggleLoading()
        viewModel.removeFromWatchList(tvShow)

    }
}