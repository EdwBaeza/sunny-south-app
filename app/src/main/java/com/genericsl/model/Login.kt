package com.genericsl.model

import com.google.gson.annotations.SerializedName

//data class Login(@SerializedName("username") val username: String, @SerializedName("password") val password: String)
data class Login(
    val username: String,
    val password: String,
    @SerializedName("access_token")
    val access_token: String
)

