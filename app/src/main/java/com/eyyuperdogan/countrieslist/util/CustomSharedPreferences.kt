package com.eyyuperdogan.countrieslist.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object{
        private var sharedPreferences:SharedPreferences?=null
        @Volatile private var instance:CustomSharedPreferences?=null
        private val lock=Any()

        operator fun invoke(context: Context):CustomSharedPreferences=instance?: synchronized(lock){
         instance?:makeCustomSharedPreferences(context).also {
             instance=it
         }
        }



      private fun makeCustomSharedPreferences(context: Context):CustomSharedPreferences{
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
        return CustomSharedPreferences()
    }
}
    fun saveTime(time:Long){
     sharedPreferences?.edit(commit=true){
      putLong("time",time)
     }
    }

    fun getTime()= sharedPreferences?.getLong("time",0)
}