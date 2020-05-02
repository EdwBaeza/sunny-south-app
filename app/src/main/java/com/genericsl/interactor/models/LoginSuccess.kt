package com.genericsl.interactor.models

import android.text.TextUtils
import android.util.Patterns
import com.google.gson.annotations.SerializedName

class LoginSuccess (
    val user: User,
    @SerializedName("access_token")
    val access_token: String
){

    fun isDataValid(): Int {
        if (TextUtils.isEmpty(user.username))
            return 0
        else if (!Patterns.EMAIL_ADDRESS.matcher(user.username).matches())
            return 1
        else
            return -1

    }
}
