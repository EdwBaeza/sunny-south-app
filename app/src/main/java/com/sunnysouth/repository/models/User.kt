package com.sunnysouth.repository.models

import android.util.Patterns
import java.lang.NumberFormatException
import com.google.gson.annotations.SerializedName
import com.sunnysouth.repository.utils.RegexValidator
const val PHONE_NUMBER_MAX_VALUE = 10
const val PASSWORD_MIN_VALUE = 8
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
    var userErrors:MutableList<String> = arrayListOf()



    fun IsValidpersonalData():Boolean {
        this.userErrors.clear()

        if (!RegexValidator.CharactersAndNumbersOnly(firstName)
            && !RegexValidator.CharactersAndNumbersOnly(lastName))
            this.userErrors.add("The first and last name may only contain letters and numbers")

        if (firstName.isNullOrBlank() || lastName.isNullOrBlank())
            this.userErrors.add("The first and last name must have a valor")

        if(phoneNumber.length != PHONE_NUMBER_MAX_VALUE)
            this.userErrors.add("The phone number must be equal to 10")

        if (!IsOnlyNumeric(phoneNumber))
            this.userErrors.add("The phone number must be only numbers")

        return this.userErrors.isNullOrEmpty()
    }

/*    fun emailIsValid():Boolean {
        return emailIsValid(email)
    }*/

    fun IsValidcredentialData():Boolean {
        this.userErrors.clear()
        if (!RegexValidator.CharactersAndNumbersOnly(username))
            this.userErrors.add("The username may only contain letters and numbers")

        if (username.isNullOrBlank() || email.isNullOrBlank())
            this.userErrors.add("The username and email must have a valor")

        if (!emailIsValid(email))
            this.userErrors.add("the email is not formatted correctly")

        return this.userErrors.isNullOrEmpty()
    }

    private fun emailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun IsValidpasswordsData():Boolean {
        this.userErrors.clear()

        if (password.isNullOrBlank() || password.isNullOrBlank())
            this.userErrors.add("The passwords must have a valor")
        if (password.length<PASSWORD_MIN_VALUE)
            this.userErrors.add("The passwords must be at least 8 characters")
        if (!passwordsAreEquals())
            this.userErrors.add("The passwords aren't equals")
        if (IsOnlyNumeric(password))
            this.userErrors.add("The password should not be just numbers")

        return this.userErrors.isNullOrEmpty()
    }

    private fun IsOnlyNumeric(text:String):Boolean {
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




