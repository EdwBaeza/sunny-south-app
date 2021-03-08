package com.sunnysouth.repository.rest.user

import com.sunnysouth.repository.models.UpdateUser
import com.sunnysouth.repository.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserService{

    @GET("users/{username}/")
    fun getUser(@Header("Authorization") token: String, @Path("username") username: String): Call<User>

    @PATCH("users/{username}/")
    fun updateUser(@Header("Authorization") token: String, @Path("username") username: String, @Body updateUser: UpdateUser): Call<User>

    @Multipart
    @PATCH("users/{username}/profile/")
    fun uploadPhoto(@Header("Authorization") token: String, @Path("username") username: String, @Part picture: MultipartBody.Part): Call<User>
}