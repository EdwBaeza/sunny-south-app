package com.genericsl.presenter

import com.genericsl.interactor.clientRest.Register.RegisterServiceImp
import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User
import com.genericsl.view.ui.activity.IRegisterView

class RegisterPresenter(val view: IRegisterView):IRegisterPresenter {

    val interactor: RegisterServiceImp = RegisterServiceImp(this)

    override fun onRegister(user: User) {
        interactor.getRegister(user)
    }

    override fun onRegisterError(message: String) {
        view.onRegisterError(message)
    }

    override fun onRegisterSuccess(registerSuccess: RegisterSuccess?) {
        view.onRegisterSuccess(registerSuccess)
    }

}