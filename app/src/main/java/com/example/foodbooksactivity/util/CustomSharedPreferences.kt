package com.example.foodbooksactivity.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object{

        private val TIME = "zaman"
        private var sharedPreferences : SharedPreferences? =null

        @Volatile private var instance : CustomSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: doCustomSharedPreferences(context).also {
                instance = it
            }

        }

        private fun doCustomSharedPreferences(context: Context) : CustomSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }
    fun savedTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)

        }
    }

    fun getTime() = sharedPreferences?.getLong(TIME,0)

}