package com.genericsl.presenter

import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.view.ui.activity.ILoginView

class LoginPresenter(internal var iLoginView: ILoginView):ILoginPresenter {

    override fun onLogin(loginSuccess: LoginSuccess) {

        //val login = Login(email, password, "a")
        val loginCode = loginSuccess.isDataValid()

        if(loginCode == 0)
            iLoginView.onLoginError("El email no debe ser vacio")
        else if(loginCode == 1)
            iLoginView.onLoginError("Dirección email incorrecto")
        else
            iLoginView.onLoginSuccess("Inicio de sesión exitoso")

    }
}