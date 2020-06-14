

package com.genericsl

import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.interactor.models.User
import com.genericsl.presenter.ILoginPresenter
import com.genericsl.presenter.LoginPresenter
import com.genericsl.view.ui.activity.ILoginView
import com.genericsl.view.ui.activity.LoginActivity
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    factory { Login( get(), get() ) }
    factory { User() }
    factory { LoginSuccess( get(), get() ) }

    //factory { (email:String, password:String) -> Login( email, password )}
    factory<ILoginPresenter> { LoginPresenter( get()) }

    //factory { LoginServiceImp( get() ) }
    factory { ( loginPresenter : ILoginPresenter ) -> LoginServiceImp( loginPresenter ) }

    }