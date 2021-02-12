package com.stone.mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.databinding.ItemImagePagerBinding

class ImageSliderAdapter(val sliderImages:Array<String>) :RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_image_pager,parent,false))
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bind(sliderImages[position])
    }

    override fun getItemCount(): Int {
       return sliderImages.size
    }
    class ImageSliderViewHolder(itemView: ItemImagePagerBinding) :RecyclerView.ViewHolder(itemView.root){
        private val binding=itemView
        fun bind(imageUrl:String){
            binding.imageUrl=imageUrl
        }

    }
}