package com.shunsukeshoji.giticons.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shunsukeshoji.giticons.databinding.ItemIconBinding
import com.shunsukeshoji.giticons.model.User
import com.shunsukeshoji.giticons.presentation.view_holder.IconViewCellViewHolder

class MyPagedListAdapter : PagedListAdapter<User, RecyclerView.ViewHolder>(DIffCallBack) {

    companion object {
        private val DIffCallBack = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIconBinding.inflate(inflater,parent,false)
        return IconViewCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as IconViewCellViewHolder
        getItem(position)?.let { holder.bind(it) }
    }
}