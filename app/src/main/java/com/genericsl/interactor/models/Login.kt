package com.genericsl.interactor.models

import android.text.TextUtils
import android.util.Patterns

data class Login(
    var email: String,
    var password: String
){

    fun dataIsValid(): Boolean {
        return (!emailIsValid(email)) || (!passwordIsValid(password))
    }

    private fun emailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //TextUtils.isEmpty(email))

    private fun passwordIsValid(password: String): Boolean {
        return password.length >= 6
    }
}