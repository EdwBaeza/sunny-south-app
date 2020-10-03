

package com.sunnysouth

import com.sunnysouth.repository.rest.register.RegisterServiceImp
import com.sunnysouth.repository.models.Login
import com.sunnysouth.repository.models.LoginSuccess
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.presenter.IRegisterPresenter
import com.sunnysouth.presenter.RegisterPresenter
import org.koin.dsl.module

val appModule = module {

    //Models
    factory { Login( get(), get() ) }
    factory { User() }
    factory { LoginSuccess( get(), get() ) }
    factory { RegisterSuccess( get() ) }

    //----Pascual---- Login
    //factory { ( loginPresenter : ILoginPresenter ) -> LoginServiceImp( loginPresenter ) }

    //----David----Register
    factory<IRegisterPresenter> { RegisterPresenter( get() )}
    factory { (registerPresenter : IRegisterPresenter) -> RegisterServiceImp( registerPresenter ) }

    }