

package com.genericsl

import com.genericsl.interactor.clientRest.Register.RegisterServiceImp
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User
import com.genericsl.presenter.login.ILoginPresenter
import com.genericsl.presenter.IRegisterPresenter
import com.genericsl.presenter.login.LoginPresenter
import com.genericsl.presenter.RegisterPresenter
import org.koin.dsl.module

val appModule = module {

    //Models
    factory { Login( get(), get() ) }
    factory { User() }
    factory { LoginSuccess( get(), get() ) }
    factory { RegisterSuccess( get() ) }

    //----Pascual---- Login
    factory<ILoginPresenter> { LoginPresenter( get()) }
    factory { ( loginPresenter : ILoginPresenter ) -> LoginServiceImp( loginPresenter ) }

    //----David----Register
    factory<IRegisterPresenter> { RegisterPresenter( get() )}
    factory { (registerPresenter : IRegisterPresenter) -> RegisterServiceImp( registerPresenter ) }

    }