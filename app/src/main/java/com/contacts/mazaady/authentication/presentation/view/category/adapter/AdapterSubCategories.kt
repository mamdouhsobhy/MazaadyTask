package com.contacts.mazaady.authentication.presentation.view.category.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.Children
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.databinding.LayoutItemSelectorBinding

class AdapterSubCategories(private val itemSelected:(Children)->Unit) :
    ListAdapter<Children, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutItemSelectorBinding.inflate(parent.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    private inner class ViewHolder(private val binding: LayoutItemSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Children) = with(binding) {
            binding.layoutItemSelector.setOnClickListener {
                itemSelected(item)
            }
            binding.tvName.text=item.slug
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Children>() {
            override fun areItemsTheSame(
                oldItem: Children,
                newItem: Children
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Children,
                newItem: Children
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}