package com.genericsl.model.clientRest

import com.genericsl.model.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("Prod/auth")
    fun goLogin(@Body login: Login): Call<Login>

}