package com.sunnysouth.repository.models

import android.util.Patterns
import java.lang.NumberFormatException
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName

class User
{
    @SerializedName("username")
    lateinit var username: String
    @SerializedName("first_name")
    lateinit var firstName: String
    @SerializedName("last_name")
    lateinit var lastName: String
    @SerializedName("phone_number")
    lateinit var phoneNumber: String
    @SerializedName("email")
    lateinit var email: String
    lateinit var password: String
    @SerializedName("password_confirmation")
    lateinit var passwordConfirmation: String
    /*  validar password >= 6  que esta en el modelo Login
    validar phone 10 digitos
    como validar el email esta en el modelo Login
    ningun campo debe ser vacio virgilio es puto
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

    fun fullName():String{
        return  "$firstName $lastName"
    }


}


