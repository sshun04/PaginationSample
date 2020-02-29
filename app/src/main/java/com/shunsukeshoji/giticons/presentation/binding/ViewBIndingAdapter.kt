package com.shunsukeshoji.giticons.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import kotlinx.android.synthetic.main.item_icon.view.*

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("loadIconImage")
    fun loadIconImage(view: ImageView, imageUrl: String?) {
        if (imageUrl == null) return
        view.load(imageUrl)
    }
}