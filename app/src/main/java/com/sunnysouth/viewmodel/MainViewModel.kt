package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel

class MainViewModel(): BaseViewModel(){

    fun setContextApp(context: Context){
        this.context = context
    }

    fun logout(){
        val sharedPref = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun getEmail(): String? {
        val sharedPref = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        return sharedPref.getString("email", null)
    }
}