package com.stone.mvvm.activities

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.stone.mvvm.R
import com.stone.mvvm.adapters.TVShowAdapter
import com.stone.mvvm.databinding.ActivityMainBinding
import com.stone.mvvm.viewModels.MostPopularTVShowViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MostPopularTVShowViewModel>()
    private val adapter = TVShowAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        doIntilize()
    }

    private fun doIntilize() {
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
        getMostPopularTVShow()
    }

    private fun getMostPopularTVShow() {

        binding.isLoading = true
        viewModel.getMostPopularTVShow(0).observe(this, { response ->
            if (response != null) {
                binding.isLoading = false
                adapter.submitList(response.tvShows)


            }
        }
        )
    }
}