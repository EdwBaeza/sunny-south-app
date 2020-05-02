package com.genericsl.interactor.clientRest.login

import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.interactor.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginServiceImp {

    val BASE_URL = "https://ec2-54-187-153-186.us-west-2.compute.amazonaws.com/"

    fun Login(user: User){
        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.goLogin(LoginSuccess(user,"a"))?.enqueue(object : Callback<LoginSuccess> {
            override fun onFailure(call: Call<LoginSuccess>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<LoginSuccess>, response: Response<LoginSuccess>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}