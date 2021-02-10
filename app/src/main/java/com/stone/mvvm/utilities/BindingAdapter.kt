package com.stone.mvvm.utilities


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


class BindingAdapter() {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String) {
            imageView.alpha=0f
            Glide.with(imageView.context)
                .load(url)
                .apply {
                    imageView.animate().setDuration(300).alpha(1f).start()
                }
                .into(imageView)

        }
    }
}