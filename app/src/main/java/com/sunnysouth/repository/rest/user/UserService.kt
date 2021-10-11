package com.sunnysouth.repository.rest.user

import com.sunnysouth.repository.models.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService{

    @GET("users/{username}/")
    fun findByUsername(@Header("Authorization") token: String, @Path("username") username: String): Call<User>

    @GET("users/me")
    fun getCurrentUser(@Header("Authorization") token: String): Call<User>

    @PATCH("users/me")
    fun update(@Header("Authorization") token: String, @Body user: User): Call<User>

    @Multipart
    @PATCH("users/{username}/profile/")
    fun uploadPhoto(@Header("Authorization") token: String, @Path("username") username: String, @Part picture: MultipartBody.Part): Call<User>
}