package com.genericsl.interactor.clientRest.Register

import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface RegisterService {

    @POST("users/signup/")
    fun goRegister(@Body user:User): Call<RegisterSuccess>
}