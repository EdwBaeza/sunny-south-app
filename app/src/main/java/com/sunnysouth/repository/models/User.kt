package com.sunnysouth.repository.models

import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.sunnysouth.repository.utils.RegexValidator
import java.sql.Timestamp

class User {

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
    lateinit var dateJoined: Timestamp

    @SerializedName("uuid")
    lateinit var uuid: String

    @SerializedName("created")
    lateinit var created: Timestamp

    @SerializedName("modified")
    lateinit var modified: Timestamp

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("phone_number")
    lateinit var phoneNumber: String

    @SerializedName("password")
    lateinit var password: String

    @SerializedName("password_confirmation")
    lateinit var passwordConfirmation: String

    var userErrors: MutableList<String> = arrayListOf()
    val PHONE_NUMBER_MAX_VALUE = 10
    val PASSWORD_MIN_VALUE = 6

    fun isValidPersonalData():Boolean {
        this.userErrors.clear()

        if (!RegexValidator.CharactersAndNumbersOnly(firstName)
            && !RegexValidator.CharactersAndNumbersOnly(lastName))
            this.userErrors.add("The first and last name may only contain letters and numbers")

        if (firstName.isNullOrBlank() || lastName.isNullOrBlank())
            this.userErrors.add("The first and last name must have a valor")

        if(phoneNumber.length != PHONE_NUMBER_MAX_VALUE)
            this.userErrors.add("The phone number must be equal to 10")

        if (!isOnlyNumeric(phoneNumber))
            this.userErrors.add("The phone number must be only numbers")

        return this.userErrors.isNullOrEmpty()
    }

    fun isValidCredentialData():Boolean {
        this.userErrors.clear()
        if (!RegexValidator.CharactersAndNumbersOnly(username))
            this.userErrors.add("The username may only contain letters and numbers")

        if (username.isNullOrBlank())
            this.userErrors.add("The username must have a valor")

        if (!emailIsValid(email))
            this.userErrors.add("the email is not formatted correctly")

        return this.userErrors.isNullOrEmpty()
    }

    private fun emailIsValid(email: String): Boolean {
        return !email.isNullOrBlank() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPasswordsData():Boolean {
        this.userErrors.clear()

        if (password.isNullOrBlank() || password.isNullOrBlank())
            this.userErrors.add("The passwords must have a valor")
        if (password.length < PASSWORD_MIN_VALUE)
            this.userErrors.add("The passwords must be at least 8 characters")
        if (!passwordsAreEquals())
            this.userErrors.add("The passwords aren't equals")
        if (isOnlyNumeric(password))
            this.userErrors.add("The password should not be just numbers")

        return this.userErrors.isNullOrEmpty()
    }

    private fun isOnlyNumeric(text:String):Boolean {
        return RegexValidator.NumbersOnly(text)
    }

    private fun passwordsAreEquals():Boolean {
        return (password.equals(passwordConfirmation))
    }

    override fun toString(): String {
        return "$username"

    }

    fun fullName():String{
        return  "$firstName $lastName"
    }
}




