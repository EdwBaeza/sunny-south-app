package com.genericsl.interactor.clientRest.login

import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login/")
    fun goLogin(@Body login: Login): Call<LoginSuccess>

}