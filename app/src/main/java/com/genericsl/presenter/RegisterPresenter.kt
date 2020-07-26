package com.genericsl.presenter

import com.genericsl.interactor.clientRest.Register.RegisterServiceImp
import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User
import com.genericsl.view.ui.activity.register.IRegisterView
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class RegisterPresenter(val view: IRegisterView):IRegisterPresenter, KoinComponent {

    val interactor:RegisterServiceImp by inject{ parametersOf(this)  }

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