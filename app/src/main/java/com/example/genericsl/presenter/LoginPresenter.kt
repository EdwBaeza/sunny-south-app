package com.example.genericsl.presenter

import com.example.genericsl.model.User
import com.example.genericsl.view.ui.activity.ILoginView

class LoginPresenter(internal var iLoginView: ILoginView):ILoginPresenter {

    override fun onLogin(email: String, password: String) {

        val user = User(email, password)
        val loginCode = user.isDataValid()

        if(loginCode == 0)
            iLoginView.onLoginError("El email no debe ser vacio")
        else if(loginCode == 1)
            iLoginView.onLoginError("Dirección email incorrecto")
        else if(loginCode == 2)
            iLoginView.onLoginError("La contraseña debe ser mayor a 6 caracteres")
        else
            iLoginView.onLoginSuccess("Inicio de sesión exitoso")


        /*val user = User(email, password)
        val isLoginSuccess = user.isDataValid

        if(isLoginSuccess)
            iLoginView.onLoginResult("Login Success")
        else
            iLoginView.onLoginResult("Login Error")*/

    }
}