package com.genericsl.presenter

import com.genericsl.interactor.models.Login
import com.genericsl.view.ui.activity.ILoginView

class LoginPresenter(internal var iLoginView: ILoginView):ILoginPresenter {

    override fun onLogin(email: String, password: String) {

        val login = Login(email, password)
        /*val loginCode = login.isDataValid()

        if(loginCode == 0)
            iLoginView.onLoginError("El email no debe ser vacio")
        else if(loginCode == 1)
            iLoginView.onLoginError("Dirección email incorrecto")
        else
            iLoginView.onLoginSuccess("Inicio de sesión exitoso")*/

    }

    override fun messa(message: String){
        iLoginView.onLoginSuccess(message)
    }
}