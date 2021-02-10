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
import com.stone.mvvm.models.TVShow

class TVShowAdapter :ListAdapter<TVShow,TVShowAdapter.TVShowViewHolder>(
    object :DiffUtil.ItemCallback<TVShow>(){
        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem==newItem
        }

    }
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val holder=TVShowViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_movie,parent,false))
        return holder
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow=currentList[position]
        holder.bindTVShow(tvShow)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
    class TVShowViewHolder(itemView: ItemMovieBinding) :RecyclerView.ViewHolder(itemView.root){
        private val binding=itemView
        fun bindTVShow(tvShow: TVShow){
            binding.tvShow=tvShow
            binding.executePendingBindings()
        }

    }
}