package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunnysouth.repository.models.LoginSuccess
import com.sunnysouth.repository.rest.register.RegisterRepository
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.login.LoginRepository
import org.koin.core.parameter.parametersOf

class RegisterViewModel(): ViewModel(){

    private lateinit var context: Context
    var service: RegisterRepository = RegisterRepository(this)
    var registerSuccess: MutableLiveData<RegisterSuccess> = MutableLiveData()
    var registerError: MutableLiveData<MutableList<String>> = MutableLiveData()
    var authenticationState: MutableLiveData<RegisterViewModel.RegisterState> = MutableLiveData()

    enum class RegisterState {
        OK,        // The register is successfully
        INVALID  // The register is failed
    }

    fun setContextApp(context: Context){
        this.context = context
    }

     fun onRegister(user: User) {
         service.getRegister(user, this.context)
    }

     fun setRegisterError(messages:MutableList<String>) {
         this.registerError.value = messages
         this.setAuthenticationState(RegisterViewModel.RegisterState.INVALID)
     }

     fun setRegisterSuccess(registerSuccess: RegisterSuccess?) {
         this.registerSuccess.value = registerSuccess
         this.setAuthenticationState(RegisterViewModel.RegisterState.OK)
    }

    private fun setAuthenticationState(authenticationState: RegisterViewModel.RegisterState){
        this.authenticationState.value = authenticationState
    }

}