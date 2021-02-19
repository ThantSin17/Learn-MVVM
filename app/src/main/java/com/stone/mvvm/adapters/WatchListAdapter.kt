package com.stone.mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.databinding.ItemMovieBinding
import com.stone.mvvm.listeners.WatchListListener
import com.stone.mvvm.models.TVShow

class WatchListAdapter(private val listener:WatchListListener) :ListAdapter<TVShow,WatchListAdapter.WatchListViewHolder>(
   object :DiffUtil.ItemCallback<TVShow>(){
       override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
           return oldItem==newItem
       }

       override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
           return oldItem==newItem
       }

   }
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        return WatchListViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_movie,parent,false
        ))
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class WatchListViewHolder(itemView: ItemMovieBinding) :RecyclerView.ViewHolder(itemView.root){
        val binding=itemView
        fun bind(tvShow: TVShow){
            binding.imgDelete.visibility=View.VISIBLE
            binding.tvShow=tvShow
            binding.imgDelete.setOnClickListener {
                listener.removeImgClick(tvShow)
            }
            binding.root.setOnClickListener {
                listener.tvShowClick(tvShow = tvShow)
            }
        }
    }
}