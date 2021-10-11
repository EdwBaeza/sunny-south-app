package com.sunnysouth.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.user.UserRepository
import okhttp3.MultipartBody

class UserProfileViewModel(): BaseViewModel(){

    var repository = UserRepository(this)
    var userError = MutableLiveData<String>()
    var user = MutableLiveData<User>()

    fun setUserError(error: String){
        this.userError.value = error
    }

    fun setSessionUser(user: User){
        this.user.value = user
    }

    fun getCurrentUser(){
        val (token, _) = getUserPreferences()
        this.repository.getCurrentUser(token, this.context)
    }

    fun update(user: User){
        val (token, _) = getUserPreferences()
        this.repository.update(token, user, this.context)
    }

    fun uploadPhoto(picture: MultipartBody.Part){
        val (token, username) = getUserPreferences()
        this.repository.uploadPhoto(token, username, picture, this.context)
    }

}