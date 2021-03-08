package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName

class LoginSuccess (
    val user: User,
    @SerializedName("access_token")
    val accessToken: String
)
