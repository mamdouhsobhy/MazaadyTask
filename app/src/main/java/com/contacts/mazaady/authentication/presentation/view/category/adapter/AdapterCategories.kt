package com.contacts.mazaady.authentication.presentation.view.category.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.Category
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.databinding.LayoutItemSelectorBinding

class AdapterCategories(private val itemSelected:(Category)->Unit) :
    ListAdapter<Category, RecyclerView.ViewHolder>(
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
        fun bind(item: Category) = with(binding) {
            binding.layoutItemSelector.setOnClickListener {
                itemSelected(item)
            }
            binding.tvName.text=item.slug
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}