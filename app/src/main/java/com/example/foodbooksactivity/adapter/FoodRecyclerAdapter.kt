package com.example.foodbooksactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbooksactivity.databinding.FoodRecylerRowBinding
import com.example.foodbooksactivity.model.Food
import com.example.foodbooksactivity.util.doPlaceHolder
import com.example.foodbooksactivity.util.downloadPicture
import com.example.foodbooksactivity.view.FoodListFragmentDirections

class FoodRecyclerAdapter(val foodList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: FoodRecylerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            FoodViewHolder = FoodViewHolder(
        FoodRecylerRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.name.text = foodList.get(position).foodName
        holder.binding.calories.text = foodList.get(position).foodCalories

        //görsel kısım eklenecek

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailsFragment(
                foodList.get(position).uuid
            )
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadPicture(foodList.get(position).foodPicture, doPlaceHolder(holder.itemView.context)
        )

    }

    fun foodListUpdate(newFoodList: List<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}
