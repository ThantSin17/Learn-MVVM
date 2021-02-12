package com.stone.mvvm.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.stone.mvvm.R
import com.stone.mvvm.databinding.ActivityTvShowDetailBinding
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.TVShowDetailsViewModel

class TVShowDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailBinding
    private val viewModel by viewModels<TVShowDetailsViewModel>()
    companion object{
        fun gotoTVShowActivity(context: Context,tvShow: TVShow):Intent{
            val intent=Intent(context,TVShowDetailActivity::class.java)
            intent.putExtra("tvShow",tvShow)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_detail)
        val tvShow= intent.getSerializableExtra("tvShow") as TVShow
        toggleLoading()
        viewModel.getTVShowDetails(tvShow.id.toString()).observe(this,{ response ->
            if (response!=null){

                toggleLoading()
                Toast.makeText(this,response.tvShowDetails.toString(),Toast.LENGTH_SHORT).show()
            }

        })



    }

    private fun toggleLoading() {
        when (binding.isLoading) {
            null -> binding.isLoading = true
            false-> binding.isLoading = true
            true -> binding.isLoading = false
        }
    }
}