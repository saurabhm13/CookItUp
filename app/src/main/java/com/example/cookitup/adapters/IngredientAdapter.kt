package com.example.cookitup.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookitup.data.IngredientData
import com.example.cookitup.databinding.IngredientItemListBinding

class IngredientAdapter(): RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var ingredientList = ArrayList<IngredientData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setIngredientList(ingredientList: List<IngredientData>) {
        this.ingredientList = ingredientList as ArrayList<IngredientData>
        notifyDataSetChanged()
    }

    class IngredientViewHolder(val binding: IngredientItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(IngredientItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
//        Glide.with(holder.itemView)
//            .load(ingredientList[position].ingredientImage)
//            .into(holder.binding.ingredientImage)

        Glide.with(holder.itemView)
            .load("https://www.themealdb.com/images/ingredients/${ingredientList[position].ingredientName}.png")
            .into(holder.binding.ingredientImage)

        holder.binding.ingredientName.text = ingredientList[position].ingredientName
        holder.binding.ingredientMeasurement.text = ingredientList[position].ingredientMeasurement
    }
}