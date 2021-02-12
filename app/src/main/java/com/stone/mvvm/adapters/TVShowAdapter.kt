package com.stone.mvvm.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.databinding.ItemMovieBinding
import com.stone.mvvm.listeners.TVShowListener
import com.stone.mvvm.models.TVShow

class TVShowAdapter( val tvShowListener: TVShowListener) :ListAdapter<TVShow,TVShowAdapter.TVShowViewHolder>(
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
        return TVShowViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {

        holder.bindTVShow(currentList[position])
        Log.i("adapterSize",currentList.size.toString())
    }

    override fun getItemViewType(position: Int): Int {
        return currentList.size
    }
    override fun getItemCount(): Int {
        return currentList.size
    }
   inner class TVShowViewHolder(itemView: ItemMovieBinding) :RecyclerView.ViewHolder(itemView.root){
        private val binding=itemView
        fun bindTVShow(tvShow: TVShow){
            binding.tvShow=tvShow
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                tvShowListener.onClickTVShow(tvShow)
            }
        }

    }
}