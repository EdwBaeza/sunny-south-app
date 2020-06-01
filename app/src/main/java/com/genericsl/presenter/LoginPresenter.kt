package com.genericsl.presenter

import android.content.Context
import com.genericsl.R
import com.genericsl.interactor.models.Login
import com.genericsl.view.ui.activity.ILoginView
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.LoginSuccess

class LoginPresenter(val view: ILoginView):ILoginPresenter {

    val interactor: LoginServiceImp = LoginServiceImp(this)

    override fun onLogin(login: Login, context: Context) {

        if(login.dataIsValid())
            view.onLoginError(context.getString(R.string.data_invalid))
        else
            interactor.onLogin(login, context)
    }

    override fun onLoginSuccess(loginSuccess: LoginSuccess?){
        view.onLoginSuccess(loginSuccess)
    }

    override fun onLoginError(message: String) {
        view.onLoginError(message)
    }

    override fun saveDataLogin(login: LoginSuccess?, context: Context){
        interactor.saveDataLogin(login,context)
    }
}