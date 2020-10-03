package com.sunnysouth.repository.models

import android.util.Patterns
import java.lang.NumberFormatException
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class User : BaseObservable()
{
    lateinit var username: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var phoneNumber: String

    var email: String = ""
        @Bindable get() = field
        set(email) {
            field=email
        }

    lateinit var password: String
    lateinit var passwordConfirmation: String
    /*  validar password >= 6  que esta en el modelo Login
    validar phone 10 digitos
    como validar el email esta en el modelo Login
    ningun campo debe ser vacio
*/

    fun personalDataIsValid():Boolean {
        if (firstName.isNullOrBlank() || lastName.isNullOrBlank())
            return false
        return true
    }

    fun emailIsValid():Boolean {
        return emailIsValid(email)
    }

    fun credentialDataIsValid():Boolean {
        if (username.isNullOrBlank() || email.isNullOrBlank())
            return false
        return true
    }

    private fun emailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun phoneNumberValidation():Boolean {
        return (phoneNumber.length == 10)
    }

    fun passwordsDataIsValid():Boolean {
        if (password.isNullOrBlank() || password.isNullOrBlank())
            return false
        else if (password.length<8)
            return false
        return true
    }

    fun passwordIsOnlyNumeric():Boolean {
        try {
            Integer.parseInt(password)
        }
        catch(e:NumberFormatException) {
            return false
        }
        return true
    }

    fun passwordsAreEquals():Boolean {
        return (password.equals(passwordConfirmation))
    }

    override fun toString(): String {
        return "$username"

    }


}


