package com.example.foodbooksactivity.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodbooksactivity.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase : RoomDatabase(){

    abstract fun foodDao(): FoodDAO

    //Singleton

    companion object{

        @Volatile private var instance : FoodDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabes(context).also {
                instance = it

            }
        }

        private fun createDatabes(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java,"foodDatabase"
            ).build()
    }


}