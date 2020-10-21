package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunnysouth.repository.models.UpdateUser
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.user.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    fun setSessionUser(user: User){
        this.user.value = user
    }

    fun getSessionUser(){
        val data = getUserPreferences()
        this.repository.getUser(data[0], data[1], this.context)
    }

    fun updateUser(updateUser: UpdateUser){
        val data = getUserPreferences()
        this.repository.updateUser(data[0], data[1], updateUser ,this.context)
    }

    fun uploadPhoto(picture: MultipartBody.Part){
        val data = getUserPreferences()
        this.repository.uploadPhoto(data[0], data[1], picture, this.context)
    }

    private fun getUserPreferences(): Array<String> {
        val sharedPref = this.context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val username : String = sharedPref.getString("username", "").toString()
        val token : String = sharedPref.getString("token", "").toString()
        val tokenRequest : String = "Token $token"

        if (username.isNullOrEmpty() || token.isNullOrEmpty())
            throw IllegalArgumentException("User and token required")
        return arrayOf(tokenRequest, username)
    }
}