package com.genericsl.interactor.models

import android.util.Patterns
import java.lang.NumberFormatException
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class User : BaseObservable()
{
    var username: String = ""
        get() = field
        set(username) { field=username }

    var first_name: String = ""
        get() = field
        set(first_name) { field=first_name }

    var last_name: String = ""
        get() = field
        set(last_name) { field=last_name }

    var phone_number: String = ""
        get() = field
        set(phone_number) { field=phone_number }

    var email: String = ""
        @Bindable get() = field
        set(email) {
            field=email
            //notifyPropertyChanged(com.genericsl.databinding.BR.email)
        }

    var password: String = ""
        get() = field
        set(password) { field=password }

    var password_confirmation: String = ""
        get() = field
        set(password_confirmation) { field=password_confirmation }

    /*  validar password >= 6  que esta en el modelo Login
    validar phone 10 digitos
    como validar el email esta en el modelo Login
    ningun campo debe ser vacio
*/

    fun personalDataIsValid():Boolean
    {
        if (first_name.isNullOrBlank() || last_name.isNullOrBlank())
            return false
        return true
    }

    fun emailIsValid():Boolean
    {
        return emailIsValid(email)
    }

    fun credentialDataIsValid():Boolean
    {
        if (username.isNullOrBlank() || email.isNullOrBlank())
            return false
        return true
    }

    private fun emailIsValid(emailComparation: String): Boolean {
        var result = Patterns.EMAIL_ADDRESS.matcher(emailComparation).matches()
        return Patterns.EMAIL_ADDRESS.matcher(emailComparation).matches()
    }

    fun phoneNumberValidation():Boolean
    {
        if (phone_number.length.equals(10))
            return true
        return false
    }

    fun passwordsDataIsValid():Boolean
    {
        if (password.isNullOrBlank() || password_confirmation.isNullOrBlank())
            return false
        else if (password.length<8)
            return false
        return true
    }

    fun passwordIsOnlyNumeric():Boolean
    {
        try
        {
            Integer.parseInt(password)
        }
        catch(e:NumberFormatException)
        {
            return false
        }

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


