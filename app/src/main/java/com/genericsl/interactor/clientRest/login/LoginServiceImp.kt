package com.genericsl.interactor.clientRest.login

import com.genericsl.R
import com.genericsl.config.Config
import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.presenter.ILoginPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginServiceImp(val presenter: ILoginPresenter) {

    fun onLogin(login: Login){

        val retrofit = RetrofitClient().getClient(Config.BASE_URL)
        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.goLogin(login)?.enqueue(object : Callback<LoginSuccess>{
            override fun onFailure(call: Call<LoginSuccess>, t: Throwable) {
                presenter.onLoginError((R.string.error_in_login).toString())
            }

            override fun onResponse(call: Call<LoginSuccess>, response: Response<LoginSuccess>) {

                if(response.code() != 200){
                    presenter.onLoginError((R.string.error_data_user).toString())
                }else{
                    val loginSuccess: LoginSuccess? = response.body()
                    presenter.onLoginSuccess(loginSuccess)
                }
            }
        })
    }
}