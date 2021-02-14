package com.stone.mvvm.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.stone.mvvm.R
import com.stone.mvvm.adapters.ImageSliderAdapter
import com.stone.mvvm.databinding.ActivityTvShowDetailBinding
import com.stone.mvvm.models.TVShow
import com.stone.mvvm.viewModels.TVShowDetailsViewModel
import java.util.*

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
        binding.tvShow=tvShow
        toggleLoading()
        viewModel.getTVShowDetails(tvShow.id.toString()).observe(this,{ response ->
            if (response!=null){

                toggleLoading()
                loadImageSlider(response.tvShowDetails.pictures)
                binding.description = HtmlCompat.fromHtml(response.tvShowDetails.description,HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                binding.rating=String.format(Locale.getDefault(),"%.2f",(response.tvShowDetails.rating).toDouble())
                binding.genre=response.tvShowDetails.geners[0]
                binding.runtime=response.tvShowDetails.runtime
                binding.btnWebsite.setOnClickListener {
                    val intent=Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(response.tvShowDetails.url)
                    startActivity(intent)
                }

//              Toast.makeText(this,response.tvShowDetails.toString(),Toast.LENGTH_SHORT).show()
            }

        })

        binding.textReadMore.setOnClickListener {
            if (binding.textReadMore.text.toString() == "Read More"){
                binding.textDescription.maxLines= Int.MAX_VALUE
                binding.textDescription.ellipsize=null
                binding.textReadMore.text=getString(R.string.read_les)
            }else{
                binding.textDescription.maxLines= 4
                binding.textDescription.ellipsize=TextUtils.TruncateAt.END
                binding.textReadMore.text=getString(R.string.read_more)
            }
        }


        binding.imageBack.setOnClickListener {
            onBackPressed()
        }



    }

    private fun loadBinding() {
      binding.layoutContainer.visibility=View.VISIBLE
    }

    private fun loadImageSlider(sliderImages:Array<String>) {
        binding.sliderViewPager.offscreenPageLimit=1
        binding.sliderViewPager.adapter=ImageSliderAdapter(sliderImages)


        setupSliderIndicator(sliderImages.size)
        loadBinding()
        binding.sliderViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }
    private fun setupSliderIndicator(count:Int){
        val indicator:Array<ImageView?> = arrayOfNulls(count)
        val layoutParam=LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParam.setMargins(8,0,8,0)
        for (i in indicator.indices){
            indicator[i]= ImageView(applicationContext)
            indicator[i]?.setImageDrawable(ContextCompat.getDrawable(
                applicationContext,
                R.drawable.background_slider_indicator
            ))
            indicator[i]?.layoutParams=layoutParam
            binding.layoutIndicator.addView(indicator[i])
        }

        setCurrentSliderIndicator(0)


    }
    private fun setCurrentSliderIndicator(position:Int){
        val child=binding.layoutIndicator.childCount
        for (i in 0 until child){
            val imageView=binding.layoutIndicator.getChildAt(i) as ImageView
            if (i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext,R.drawable.background_slider_indicator_active
                ))
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext,R.drawable.background_slider_indicator
                ))
            }
        }

    }


    private fun toggleLoading() {
        when (binding.isLoading) {
            null -> binding.isLoading = true
            false-> binding.isLoading = true
            true -> binding.isLoading = false
        }
    }

}