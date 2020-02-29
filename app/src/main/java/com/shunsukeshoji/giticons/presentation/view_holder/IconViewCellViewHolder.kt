package com.shunsukeshoji.giticons.presentation.view_holder


import androidx.recyclerview.widget.RecyclerView
import com.shunsukeshoji.giticons.model.User
import com.shunsukeshoji.giticons.databinding.ItemIconBinding

class IconViewCellViewHolder(
    private val binding :ItemIconBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.user = user
        binding.executePendingBindings()
    }
}