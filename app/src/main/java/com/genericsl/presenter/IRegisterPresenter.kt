package com.genericsl.presenter

import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User

interface IRegisterPresenter {

    fun onRegister(user: User)
    fun onRegisterError(message: String)
    fun onRegisterSuccess(registerSuccess: RegisterSuccess?)

}