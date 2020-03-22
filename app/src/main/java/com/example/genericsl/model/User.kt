package com.example.genericsl.model

import android.text.TextUtils
import android.util.Patterns

class User(override val email:String, override  val password:String):IUser {

    override fun isDataValid(): Int {
        if (TextUtils.isEmpty(email))
            return 0
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return 1
        else if (password.length <= 6)
            return 2
        else
            return -1

    }

    /*override val isDataValid: Boolean

        get() = (!TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length > 6)*/
}