package com.stone.mvvm.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.adapters.TVShowAdapter
import com.stone.mvvm.databinding.ActivitySearchTvshowBinding
import com.stone.mvvm.listeners.TVShowListener
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.SearchTVShowViewModel
import java.util.*

class SearchTVShowActivity : AppCompatActivity(), TVShowListener {
    companion object{
        fun gotoSearchTVShowActivity(context: Context):Intent{
            return Intent(context,SearchTVShowActivity::class.java)
        }
    }

    private var currentPage=1
    private var totalPages=1
    private lateinit var binding:ActivitySearchTvshowBinding
    private val viewModel by viewModels<SearchTVShowViewModel>()
    private val adapter=TVShowAdapter(this)
    private var timer:Timer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search_tvshow)


        binding.searchRecycler.setHasFixedSize(true)
        binding.searchRecycler.adapter=adapter

        binding.searchRecycler.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.searchRecycler.canScrollVertically(1)){
                    if(binding.inputSearch.text.toString().isNotEmpty()){
                        if (currentPage<totalPages){
                            ++currentPage
                            searchTVShow(binding.inputSearch.text.toString())
                        }
                    }
                }

            }
        })
        binding.inputSearch.requestFocus()

        doOnClick()
    }

    private fun searchTVShow(query:String){
        toggleLoading()
        viewModel.searchTVShow(query,currentPage).observe(this,{
            toggleLoading()
            if (it!=null){
                totalPages=it.totalPages
                adapter.submitList(it.tvShows)
            }
        })

    }
    private fun doOnClick() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        binding.inputSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()){
                    timer= Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalPages = 1
                                searchTVShow(s.toString())
                            }
                        }
                    },5000)
                }
            }
        })
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