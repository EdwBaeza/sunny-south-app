package com.genericsl.presenter

import com.genericsl.interactor.models.Login
import com.genericsl.view.ui.activity.ILoginView
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.LoginSuccess

class LoginPresenter(val view: ILoginView):ILoginPresenter {

    val interactor: LoginServiceImp = LoginServiceImp(this)

    override fun onLogin(login: Login) {
        val loginUser = Login(login.email, login.password)

        if(loginUser.dataIsValid() == 1)
            view.onLoginError("Los datos ingresados no son válidos")
        else
            interactor.getLogin(login)
    }

    override fun onLoginSuccess(loginSuccess: LoginSuccess?){
        view.onLoginSuccess(loginSuccess)
    }

    override fun onLoginError(message: String) {
        view.onLoginError(message)
    }
}