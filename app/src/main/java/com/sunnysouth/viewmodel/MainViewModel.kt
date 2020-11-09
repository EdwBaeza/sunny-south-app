package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel


class MainViewModel: ViewModel(){

    private lateinit var context: Context

    fun setContextApp(context: Context){
        this.context = context
    }

    fun logout(){
        val sharedPref = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}