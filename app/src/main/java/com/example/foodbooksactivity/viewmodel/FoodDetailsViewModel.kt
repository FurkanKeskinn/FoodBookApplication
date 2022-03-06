package com.example.foodbooksactivity.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooksactivity.model.Food
import com.example.foodbooksactivity.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailsViewModel(application: Application) : BaseViewModel(application){

    val foodLiveData = MutableLiveData<Food>()

    fun roomGetData(uuid: Int){
        //val banana = Food("Muz","100","10","5","1","www.test.com")
        //foodLiveData.value = banana

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }

    }
}