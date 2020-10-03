package com.sunnysouth.repository.rest.register

import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface RegisterService {

    @POST("users/signup/")
    fun goRegister(@Body user:User): Call<RegisterSuccess>
}