package com.genericsl.presenter

import com.genericsl.R
import com.genericsl.interactor.models.Login
import com.genericsl.view.ui.activity.ILoginView
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.LoginSuccess

class LoginPresenter(val view: ILoginView):ILoginPresenter {

    val interactor: LoginServiceImp = LoginServiceImp(this)

    override fun onLogin(login: Login) {

        //if(login.dataIsValid())
          //  view.onLoginError((R.string.error_data_user).toString())
        //else
            interactor.onLogin(login)
    }

    override fun onLoginSuccess(loginSuccess: LoginSuccess?){
        view.onLoginSuccess(loginSuccess)
    }

    override fun onLoginError(message: String) {
        view.onLoginError(message)
    }
}