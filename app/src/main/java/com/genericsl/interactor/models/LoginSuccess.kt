package com.genericsl.interactor.models

import com.google.gson.annotations.SerializedName

class LoginSuccess (
    val user: User,
    @SerializedName("access_token")
    val access_token: String
)
