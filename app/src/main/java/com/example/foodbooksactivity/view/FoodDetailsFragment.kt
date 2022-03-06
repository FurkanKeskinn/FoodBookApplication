package com.example.foodbooksactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodbooksactivity.databinding.FragmentFoodDetailsBinding
import com.example.foodbooksactivity.util.doPlaceHolder
import com.example.foodbooksactivity.util.downloadPicture
import com.example.foodbooksactivity.viewmodel.FoodDetailsViewModel


class FoodDetailsFragment : Fragment() {
    private var _binding: FragmentFoodDetailsBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FoodDetailsViewModel
    private var foodId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            FoodDetailsFragmentArgs.fromBundle(it).foodId
            println(foodId)
        }
        viewModel = ViewModelProviders.of(this).get(FoodDetailsViewModel::class.java)
        viewModel.roomGetData(foodId)


        observeLiveData()

    }

    fun observeLiveData() {
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {

                binding?.foodName?.text = it.foodName
                binding?.foodCalories?.text = it.foodCalories
                binding?.foodCarbohydrate?.text = it.foodCarbohydrate
                binding?.foodProtein?.text = it.foodProtein
                binding?.foodFat?.text = it.foodFat
                context?.let {
                    binding?.foodImage?.downloadPicture(food.foodPicture, doPlaceHolder(it))
                }

            }
        })
    }


}