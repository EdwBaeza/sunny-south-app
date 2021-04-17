package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunnysouth.repository.rest.register.RegisterRepository
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User


class RegisterViewModel(): BaseViewModel(){

    private var service  = RegisterRepository(this)
    var registerSuccess = MutableLiveData<RegisterSuccess>()
    var registerError = MutableLiveData<MutableList<String>>()
    var authenticationState = MutableLiveData<RegisterState>()
    var user: MutableLiveData<User> = MutableLiveData()

    init{
        user.value = User()
    }

    enum class RegisterState {
        OK,        // The register is successfully
        INVALID,  // The register is failed
        NONE
    }

     fun onRegister(user: User) {
         service.getRegister(user, this.context)
    }

     fun setRegisterError(messages:MutableList<String>) {
         this.registerError.value = messages
         this.setAuthenticationState(RegisterState.INVALID)
     }

     fun setRegisterSuccess(registerSuccess: RegisterSuccess?) {
         this.registerSuccess.value = registerSuccess
         this.setAuthenticationState(RegisterState.OK)
    }

    fun setUser(user:User){
        this.user.value = user
    }

    fun setUserCredentialsData(userName:String, email:String){
        user.value?.username = userName
        user.value?.email = email
    }

    fun setUserPersonalData(firstName:String, lastName:String, phoneNumber:String){
        user.value?.firstName = firstName
        user.value?.lastName = lastName
        user.value?.phoneNumber = phoneNumber
    }

    fun setUserPassWordData(passWord:String, passwordConfirmation:String){
        user.value?.password = passWord
        user.value?.passwordConfirmation = passwordConfirmation
    }

    fun clear() {
        this.setAuthenticationState(RegisterState.NONE)
    }

    private fun setAuthenticationState(authenticationState: RegisterState){
        this.authenticationState.value = authenticationState
    }

}