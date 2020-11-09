package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.user.UserRepository

class UserProfileViewModel (): ViewModel(){

    private lateinit var context: Context
    var repository: UserRepository = UserRepository(this)
    var userError: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()

    fun setContextApp(context: Context){
        this.context = context
    }

    fun setUserError(error: String){
        this.userError.value = error
    }

    fun getSessionUser(){
        val sharedPref = this.context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val username : String = sharedPref.getString("username", "").toString()
        val token : String = sharedPref.getString("token", "").toString()
        val tokenRequest : String = "Token $token"

        if (username.isNullOrEmpty() || token.isNullOrEmpty())
            throw IllegalArgumentException("User and token required")
        this.repository.getUser(tokenRequest, username, this.context)
    }

    fun setSessionUser(user: User){
        this.user.value = user
    }

}