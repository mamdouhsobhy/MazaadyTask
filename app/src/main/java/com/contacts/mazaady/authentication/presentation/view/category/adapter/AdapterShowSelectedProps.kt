package com.contacts.mazaady.authentication.presentation.view.category.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.authentication.presentation.view.category.model.ModelSelectedProps
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.databinding.LayoutShowPropsItemBinding

class AdapterShowSelectedProps :
    ListAdapter<ModelSelectedProps, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutShowPropsItemBinding.inflate(parent.layoutInflater, parent, false)
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

    private inner class ViewHolder(private val binding: LayoutShowPropsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSelectedProps) = with(binding) {
           tvTitle.text = item.slug
           tvValue.text = item.value
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelSelectedProps>() {
            override fun areItemsTheSame(
                oldItem: ModelSelectedProps,
                newItem: ModelSelectedProps
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ModelSelectedProps,
                newItem: ModelSelectedProps
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}