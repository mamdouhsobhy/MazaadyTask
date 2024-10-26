package com.contacts.mazaady.home.home.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.databinding.LayoutItemVideosBinding

class AdapterLiveVideos :
    ListAdapter<String, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutItemVideosBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

//        getItem(position)?.let {
//            (holder as ViewHolder).bind(it)
//        }
    }

    private inner class ViewHolder(private val binding: LayoutItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
    
}