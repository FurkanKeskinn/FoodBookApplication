package com.example.foodbooksactivity.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodbooksactivity.model.Food

@Dao //--> bunu belirtmemiz gerekli
interface FoodDAO {

    //Data Access Object (veri erişim objesi)

    @Insert
    suspend fun insertAll(vararg food: Food): List<Long>

    //Insert işlemi yapıldı ->Roomdan geliyor, insert into işlemi yapmamız için gerekli
    //suspend -> coroutine konseptinde çağıralıcağı için suspend yapabiliyoruz
    //vararg -> birden fazla ve istediğimiz sayıda besin objesini buraya vermemize olanak sağlar
    //List<Long> -> long döndürmesinin sebebi id'lerdir


    @Query("SELECT * FROM food")
    suspend fun getAllFood(): List<Food>

    @Query("SELECT * FROM food WHERE uuid = :foodId")
    suspend fun getFood(foodId : Int): Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFood()

}