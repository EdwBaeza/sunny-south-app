package com.genericsl.interactor.models

import android.text.TextUtils
import android.util.Patterns

data class Login(
    var email: String,
    var password: String
){

    fun dataIsValid(): Int {
        if ((emailIsValid(email) == 1) && (passwordIsValid(password) == 1))
            return 0
        else
            return 1
    }

    fun emailIsValid(email: String): Int {
        if (TextUtils.isEmpty(email))
            return 0
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return 0
        else
            return 1
    }

    fun passwordIsValid(password: String): Int {
        if (password.length >= 6)
            return 1
        else
            return 0
    }
}