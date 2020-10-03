package com.sunnysouth.presenter

import com.sunnysouth.repository.rest.register.RegisterServiceImp
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.view.ui.activity.RegisterActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class RegisterPresenter(val view: RegisterActivity):IRegisterPresenter, KoinComponent {

    val interactor: RegisterServiceImp by inject{ parametersOf(this)  }

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