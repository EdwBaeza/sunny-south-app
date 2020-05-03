package com.genericsl.interactor.clientRest.login


import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.models.Login
import com.genericsl.presenter.ILoginPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginServiceImp {

    internal  lateinit var loginPresenter: ILoginPresenter

    fun getLogin(email:String, password:String){

        val retrofit = RetrofitClient().getClient("https://ec2-54-187-153-186.us-west-2.compute.amazonaws.com/")

        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.goLogin(
            Login(
                email,
                password
            )
        )?.enqueue(object : Callback<Login>{
            override fun onFailure(call: Call<Login>, t: Throwable) {
                loginPresenter.messa("no funciono")
            }

            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                loginPresenter.messa("si funciono")
            }

        })

    }
}