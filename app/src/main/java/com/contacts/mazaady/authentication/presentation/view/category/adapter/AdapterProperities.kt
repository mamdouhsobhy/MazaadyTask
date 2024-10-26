package com.contacts.mazaady.authentication.presentation.view.category.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.R
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.core.presentation.extensions.gone
import com.contacts.mazaady.core.presentation.extensions.layoutInflater
import com.contacts.mazaady.core.presentation.extensions.onTextChange
import com.contacts.mazaady.core.presentation.extensions.show
import com.contacts.mazaady.databinding.LayoutInputItemBinding

class AdapterProperities(private val itemSelected:(ModelGetPropritiesResponseRemote,Int)->Unit) :
    ListAdapter<ModelGetPropritiesResponseRemote, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            LayoutInputItemBinding.inflate(parent.layoutInflater, parent, false)
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

    private inner class ViewHolder(private val binding: LayoutInputItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: ModelGetPropritiesResponseRemote) = with(binding) {
            val drawable = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_down)

                tvInputName.text = item.slug
                edInputName.hint = item.slug
                edInputName.setText(item.value)
                edSpecifyHere.setText(item.other_value)

                if(item.options?.isNotEmpty() == true){
                    edInputName.isFocusable = false
                    edInputName.isClickable = true
                    edInputName.isFocusableInTouchMode = false
                    edInputName.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                }else{
                    edInputName.isFocusable = true
                    edInputName.isClickable = false
                    edInputName.isFocusableInTouchMode = true
                    edInputName.isEnabled = true
                    edInputName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }

            if(item.isOtherValue){
                edSpecifyHere.onTextChange {
                    currentList[layoutPosition].other_value = it
                }
                layoutSpecifyHere.show()
            }else{
                if(edInputName.isFocusableInTouchMode) {
                    edInputName.onTextChange {
                        currentList[layoutPosition].value = it
                    }
                }
                layoutSpecifyHere.gone()
            }

            edInputName.setOnClickListener {
                if(item.options?.isNotEmpty() == true){
                    itemSelected(item,layoutPosition)
                }
            }


        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ModelGetPropritiesResponseRemote>() {
            override fun areItemsTheSame(
                oldItem: ModelGetPropritiesResponseRemote,
                newItem: ModelGetPropritiesResponseRemote
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ModelGetPropritiesResponseRemote,
                newItem: ModelGetPropritiesResponseRemote
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}