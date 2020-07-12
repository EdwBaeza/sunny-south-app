package com.genericsl.presenter

import android.content.Context
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess

interface ILoginPresenter {

    fun onLogin(login: Login, context: Context)
    fun onLoginError(message: String)
    fun onLoginSuccess(loginSuccess: LoginSuccess?)
    fun saveDataLogin(login: LoginSuccess?, context: Context)
}