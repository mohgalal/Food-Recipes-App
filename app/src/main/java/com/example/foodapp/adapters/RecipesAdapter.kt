package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.util.RecipesDiffUtil
import com.example.foodapp.databinding.RecipesRowLayoutBinding
import com.example.foodapp.models.FoodRecipes
import com.example.foodapp.models.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    var recipes = emptyList<Result>()

    class MyViewHolder(private var binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent : ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                var binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesAdapter.MyViewHolder, position: Int) {
        var currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun setData(newData : FoodRecipes){

        var recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        var diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }

}