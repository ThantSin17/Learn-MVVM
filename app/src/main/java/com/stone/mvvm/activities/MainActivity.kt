package com.stone.mvvm.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.adapters.TVShowAdapter
import com.stone.mvvm.databinding.ActivityMainBinding
import com.stone.mvvm.listeners.TVShowListener
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.MostPopularTVShowViewModel

class MainActivity : AppCompatActivity(), TVShowListener {
    private lateinit var binding: ActivityMainBinding
    private var currentPage = 1
    private var totalPages = 1
    private val viewModel by viewModels<MostPopularTVShowViewModel>()
    private val adapter = TVShowAdapter(this)
    private val tvShows = ArrayList<TVShow>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        toggleLoading()
        viewModel.getMostPopularTVShow(currentPage).observe(this, { response ->
            if (response != null) {
                totalPages = response.totalPages
                toggleLoading()
                tvShows.addAll(response.tvShows)
                adapter.submitList(tvShows)
                adapter.notifyDataSetChanged()

                Log.i("mainactivity", "respinse" + response.tvShows.size.toString())
                Toast.makeText(this, tvShows.size.toString(), Toast.LENGTH_LONG).show()

            }
        })
        doInitialize()
    }

    private fun doInitialize() {
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.recycler.canScrollVertically(1)){
                    if (currentPage <= totalPages) {
                        currentPage += 1
                        toggleLoading()
                        viewModel.getMostPopularTVShow(currentPage)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        binding.imageSearch.setOnClickListener {

            if (currentPage <= totalPages) {
                currentPage += 1
                toggleLoading()
                viewModel.getMostPopularTVShow(currentPage)
            }
        }

    }

    private fun toggleLoading() {
        Toast.makeText(this, "loading${binding.isLoading}", Toast.LENGTH_SHORT).show()
        if (currentPage == 1) {
            when (binding.isLoading) {
                false -> binding.isLoading = true
                true -> binding.isLoading = false
                null-> binding.isLoading=true

            }

        } else {
            when (binding.isLoadingMore) {
                true -> binding.isLoadingMore = false
               false -> binding.isLoadingMore = true
                null->binding.isLoadingMore=true
            }
        }
    }

    override fun onClickTVShow(tvShow: TVShow) {
        startActivity(TVShowDetailActivity.gotoTVShowActivity(this,tvShow))
    }
}