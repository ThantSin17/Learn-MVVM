package com.stone.mvvm.activities

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stone.mvvm.R
import com.stone.mvvm.adapters.EpisodeAdapter
import com.stone.mvvm.adapters.ImageSliderAdapter
import com.stone.mvvm.databinding.ActivityTvShowDetailBinding
import com.stone.mvvm.databinding.LayoutEpisodesBottomSheetBinding
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.TVShowDetailsViewModel
import java.util.*
import java.util.concurrent.Executors

class TVShowDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailBinding
    private val viewModel by viewModels<TVShowDetailsViewModel>()
    private lateinit var dialog: BottomSheetDialog
    private var isAddToWatchList=false
    private lateinit var episodeBinding: LayoutEpisodesBottomSheetBinding

    //    = DataBindingUtil.inflate(
//    LayoutInflater.from(this),
//    R.layout.layout_episodes_bottom_sheet,
//    findViewById(R.id.episodeContainer),
//    false
//    )
    companion object {
        fun gotoTVShowActivity(context: Context, tvShow: TVShow): Intent {
            val intent = Intent(context, TVShowDetailActivity::class.java)
            intent.putExtra("tvShow", tvShow)
            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_detail)
        val tvShow = intent.getSerializableExtra("tvShow") as TVShow
        binding.tvShow = tvShow
        episodeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.layout_episodes_bottom_sheet,
            findViewById(R.id.episodeContainer),
            false
        )
        dialog = BottomSheetDialog(this@TVShowDetailActivity)
        toggleLoading()
        viewModel.getTVShowDetails(tvShow.id.toString()).observe(this, { response ->
            if (response != null) {

                toggleLoading()
                loadImageSlider(response.tvShowDetails.pictures)
                binding.description = HtmlCompat.fromHtml(
                    response.tvShowDetails.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
                binding.rating = String.format(
                    Locale.getDefault(),
                    "%.2f",
                    (response.tvShowDetails.rating).toDouble()
                )
                binding.genre = response.tvShowDetails.geners[0]
                binding.runtime = response.tvShowDetails.runtime
                binding.btnWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(response.tvShowDetails.url)
                    startActivity(intent)
                }

                binding.btnEpisode.setOnClickListener {

                    dialog.setContentView(episodeBinding.root)
                    episodeBinding.episodeRecycler.adapter =
                        EpisodeAdapter(response.tvShowDetails.episodes)
                    episodeBinding.textTitle.text = String.format("Episode | %s", tvShow.name)


                    val frameLayout =
                        dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    if (frameLayout != null) {
                        val bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
                        bottomSheetBehavior.peekHeight =
                            Resources.getSystem().displayMetrics.heightPixels
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        Toast.makeText(this, "Expand", Toast.LENGTH_SHORT).show()
                    }

                    dialog.show()
                }
                episodeBinding.imgClose.setOnClickListener {
                    dialog.dismiss()

                }
                binding.imageWatchList.setOnClickListener {

                    if(!isAddToWatchList) {
                        isAddToWatchList=true
                        viewModel.addToWatchList(tvShow)
                        binding.imageWatchList.setImageResource(R.drawable.ic_round_check_24)
                        Toast.makeText(this, "add to watchlist", Toast.LENGTH_SHORT).show()
                    }else{
                        isAddToWatchList=false
                        viewModel.removeFromWatchList(tvShow)
                        binding.imageWatchList.setImageResource(R.drawable.ic_baseline_watchlist_24)
                        Toast.makeText(this, "remove from watchlist", Toast.LENGTH_SHORT).show()
                    }

                }
//              Toast.makeText(this,response.tvShowDetails.toString(),Toast.LENGTH_SHORT).show()
            }

        })


        viewModel.getTVShowFromWatchList(tvShowId = tvShow.id).observe(this, {
            if (it != null) {
                isAddToWatchList=true
                binding.imageWatchList.setImageResource(R.drawable.ic_round_check_24)
            }

        })
        binding.textReadMore.setOnClickListener {
            if (binding.textReadMore.text.toString() == "Read More") {
                binding.textDescription.maxLines = Int.MAX_VALUE
                binding.textDescription.ellipsize = null
                binding.textReadMore.text = getString(R.string.read_les)
            } else {
                binding.textDescription.maxLines = 4
                binding.textDescription.ellipsize = TextUtils.TruncateAt.END
                binding.textReadMore.text = getString(R.string.read_more)
            }
        }



        binding.imageBack.setOnClickListener {
            onBackPressed()
        }


    }

    private fun loadBinding() {
        binding.imageWatchList.visibility = View.VISIBLE
        binding.layoutContainer.visibility = View.VISIBLE

    }

    private fun loadImageSlider(sliderImages: Array<String>) {
        binding.sliderViewPager.offscreenPageLimit = 1
        binding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages)


        setupSliderIndicator(sliderImages.size)
        loadBinding()
        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicator(count: Int) {
        val indicator: Array<ImageView?> = arrayOfNulls(count)
        val layoutParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParam.setMargins(8, 0, 8, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(applicationContext)
            indicator[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_indicator
                )
            )
            indicator[i]?.layoutParams = layoutParam
            binding.layoutIndicator.addView(indicator[i])
        }

        setCurrentSliderIndicator(0)


    }

    private fun setCurrentSliderIndicator(position: Int) {
        val child = binding.layoutIndicator.childCount
        for (i in 0 until child) {
            val imageView = binding.layoutIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.background_slider_indicator
                    )
                )
            }
        }

    }


    private fun toggleLoading() {
        when (binding.isLoading) {
            null -> binding.isLoading = true
            false -> binding.isLoading = true
            true -> binding.isLoading = false
        }
    }

}