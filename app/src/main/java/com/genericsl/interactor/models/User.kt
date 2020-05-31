package com.genericsl.interactor.models

data class User (
    var username: String,
    var first_name: String,
    var last_name: String,
    var phone_number: String,
    var email: String,
    var password: String,
    var password_confirmation: String



){
    fun personalDataIsValid():Boolean
    {
        if (first_name.isNullOrBlank() || last_name.isNullOrBlank())
            return false
        return true
    }

    fun credentialDataIsValid():Boolean
    {
        if (username.isNullOrBlank() || email.isNullOrBlank())
            return false
        return true
    }

    fun passwordsDataIsValid():Boolean
    {
        if (password.isNullOrBlank() || password_confirmation.isNullOrBlank())
            return false
        return true
    }

    fun thePasswordsAreEquals():Boolean
    {
        if (password.equals(password_confirmation))
            return true
        return false
    }

    override fun toString(): String {
        return "$username-$first_name-$last_name-$phone_number-$email-$password-$password_confirmation"

    }
}