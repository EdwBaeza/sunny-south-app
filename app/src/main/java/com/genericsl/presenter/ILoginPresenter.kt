package com.genericsl.presenter

import com.genericsl.interactor.models.LoginSuccess

interface ILoginPresenter {

    fun onLogin(loginSuccess: LoginSuccess)

}