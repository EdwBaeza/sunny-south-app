package com.genericsl.view.ui.activity.Register

import com.genericsl.interactor.models.RegisterSuccess

interface IRegisterView {

    fun onRegisterSuccess(registerSuccess: RegisterSuccess?)
    fun onRegisterError(message:String)
    fun startHome()
}