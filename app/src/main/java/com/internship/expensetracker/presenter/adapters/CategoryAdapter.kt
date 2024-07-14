package com.internship.expensetracker.presenter.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.data.models.Category
import com.internship.expensetracker.databinding.CategoryItemBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var itemList = ArrayList<Category>()

    class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.tvCategory.text = itemList[position].categoryName
        holder.binding.imgCategory.setImageResource(itemList[position].categoryImage)
        holder.binding.imgCategory.imageTintList = ColorStateList.valueOf(itemList[position].categoryColor)
        holder.binding.cvTranImage.setBackgroundColor(itemList[position].categoryBackgroundColor)
    }

    fun updateUi(itemList: ArrayList<Category>) {
        this.itemList = itemList
    }
}