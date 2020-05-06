package com.genericsl.view.ui.activity

import com.genericsl.interactor.models.LoginSuccess

interface ILoginView {

    fun onLoginSuccess(loginSuccess: LoginSuccess?)
    fun onLoginError(message:String)
    fun startHome()
    fun saveDataLogin(login: LoginSuccess?)

}