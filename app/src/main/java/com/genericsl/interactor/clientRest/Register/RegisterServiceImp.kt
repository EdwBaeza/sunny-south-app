package com.genericsl.interactor.clientRest.Register

import com.genericsl.config.Config
import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.clientRest.login.LoginService
import com.genericsl.interactor.models.User
import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.presenter.IRegisterPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterServiceImp(val presenter:IRegisterPresenter) {

    fun getRegister(user: User){

        val retrofit = RetrofitClient().getClient(Config.BASE_URL)
        val service = retrofit?.create<RegisterService>(RegisterService::class.java)

        service?.goRegister(user)?.enqueue(object : Callback<RegisterSuccess>{
            override fun onFailure(call: Call<RegisterSuccess>, t: Throwable) {
                presenter.onRegisterError("Register error")
            }

            override fun onResponse(call: Call<RegisterSuccess>, response: Response<RegisterSuccess>) {

                if(response.code() != 201){
                    presenter.onRegisterError("Invalid Credentials")
                }else{
                    val loginSuccess: RegisterSuccess? = response.body()
                    presenter.onRegisterSuccess(loginSuccess)
                }
            }

        })

    }

}