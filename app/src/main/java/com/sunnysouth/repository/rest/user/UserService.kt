package com.sunnysouth.repository.rest.user

import com.sunnysouth.repository.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService{

    @GET("users/{username}/")
    fun getUser(@Header("Authorization") token: String, @Path("username") username: String): Call<User>
}