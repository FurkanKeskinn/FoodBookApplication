package com.example.foodbooksactivity.service

import com.example.foodbooksactivity.model.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIServices {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE_URL -> https://raw.githubusercontent.com bu bir defa tanımlanır hep kullanılır
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json bukısım ise değiken olduğu için value olarak bu koyulur

    private val BASE_URL = "https://raw.githubusercontent.com"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    fun getData() : Single<List<Food>>{
        return  api.getFood()
    }
}