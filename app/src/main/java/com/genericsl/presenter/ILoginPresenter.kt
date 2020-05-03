package com.genericsl.presenter

import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess

interface ILoginPresenter {

    fun onLogin(login: Login)
    fun onLoginError(message: String)
    fun onLoginSuccess(loginSuccess: LoginSuccess?)
}