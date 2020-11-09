package com.sunnysouth.repository.rest.login

import com.sunnysouth.repository.models.Login
import com.sunnysouth.repository.models.LoginSuccess
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login/")
    fun login(@Body login: Login): Call<LoginSuccess>
}