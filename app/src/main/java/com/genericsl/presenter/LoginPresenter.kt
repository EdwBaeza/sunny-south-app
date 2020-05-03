package com.genericsl.presenter

import com.genericsl.interactor.models.Login
import com.genericsl.view.ui.activity.ILoginView
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.LoginSuccess

class LoginPresenter(val view: ILoginView):ILoginPresenter {

    val interactor: LoginServiceImp = LoginServiceImp(this)

    override fun onLogin(login: Login) {
        interactor.getLogin(login)
        //val login = Login(email, password)

        /*val loginCode = login.isDataValid()

        if(loginCode == 0)
            iLoginView.onLoginError("El email no debe ser vacio")
        else if(loginCode == 1)
            iLoginView.onLoginError("Dirección email incorrecto")
        else
            iLoginView.onLoginSuccess("Inicio de sesión exitoso")*/
    }

    override fun onLoginSuccess(loginSuccess: LoginSuccess?){
        view.onLoginSuccess(loginSuccess)
    }

    override fun onLoginError(message: String) {
        view.onLoginError(message)
    }
}