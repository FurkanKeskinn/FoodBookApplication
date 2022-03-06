package com.example.foodbooksactivity.service

import com.example.foodbooksactivity.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {
    //retrofit kullanımı ile ilgili
    //GET, POST istekleri vardır

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE_URL -> https://raw.githubusercontent.com bu bir defa tanımlanır hep kullanılır
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json bukısım ise değiken olduğu için value olarak bu koyulur
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")


    fun getFood() : Single<List<Food>>

}