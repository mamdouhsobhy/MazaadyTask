package com.contacts.mazaady.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.contacts.mazaady.databinding.LayoutHomeSubCategoryBinding
import com.contacts.mazaady.home.home.model.ModelHomeSubCategory

class AdapterHomeSubCategory(
    private val subCategory: List<ModelHomeSubCategory>?) :

    RecyclerView.Adapter<AdapterHomeSubCategory.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: LayoutHomeSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            LayoutHomeSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.imgBaseBackground.setImageResource(subCategory?.get(position)!!.image)
    }

    override fun getItemCount() = subCategory?.size ?: 0
}