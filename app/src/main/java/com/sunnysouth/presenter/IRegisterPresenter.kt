package com.sunnysouth.presenter

import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User

interface IRegisterPresenter {

    fun onRegister(user: User)
    fun onRegisterError(message: String)
    fun onRegisterSuccess(registerSuccess: RegisterSuccess?)

}