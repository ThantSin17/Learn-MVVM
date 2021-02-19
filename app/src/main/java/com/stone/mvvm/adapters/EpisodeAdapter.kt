package com.stone.mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stone.mvvm.R
import com.stone.mvvm.databinding.ItemEpisodesBinding
import com.stone.mvvm.models.Episode

class EpisodeAdapter(private val list: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_episodes, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class EpisodeViewHolder(itemView: ItemEpisodesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(episode: Episode) {
            var title="S"
            var season=episode.season
//            if (season.length==1){
//                season= "0 $season"
//            }
            var episodeNumber=episode.episode
//            if (episodeNumber.length==1){
//                episodeNumber="0 $episodeNumber"
//            }
            episodeNumber="E $episodeNumber"
            title="$title $season $episodeNumber"
            binding.title = title
            binding.name = episode.name
            binding.airDate = episode.airDate
        }

    }
}