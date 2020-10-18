package com.sunnysouth.repository.models

import android.util.Patterns
import java.lang.NumberFormatException
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

class User
{
    lateinit var profile : Profile
    @SerializedName("last_login")
    var lastLogin: String? = null
    @SerializedName("username")
    lateinit var username: String
    @SerializedName("first_name")
    lateinit var firstName: String
    @SerializedName("last_name")
    lateinit var lastName: String
    @SerializedName("date_joined")
    var dateJoined =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("uuid")
    lateinit var uuid: String
    @SerializedName("created")
    var created =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("modified")
    var modified =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("email")
    lateinit var email: String
    @SerializedName("phone_number")
    lateinit var phoneNumber: String
    lateinit var password: String
    lateinit var passwordConfirmation: String

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


