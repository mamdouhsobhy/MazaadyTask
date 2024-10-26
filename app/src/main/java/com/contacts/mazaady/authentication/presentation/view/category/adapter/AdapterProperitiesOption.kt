package com.contacts.mazaady.authentication.presentation.view.category.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.authentication.data.responseremote.properities.Option
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.databinding.LayoutItemSelectorBinding

class AdapterProperitiesOption(private val itemSelected:(Option)->Unit) :
    ListAdapter<Option, RecyclerView.ViewHolder>(
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
        fun bind(item: Option) = with(binding) {
            binding.layoutItemSelector.setOnClickListener {
                itemSelected(item)
            }
            binding.tvName.text=item.slug
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Option>() {
            override fun areItemsTheSame(
                oldItem: Option,
                newItem: Option
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Option,
                newItem: Option
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}