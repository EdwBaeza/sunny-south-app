package com.genericsl.interactor.models

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
}



/*  validar password >= 6  que esta en el modelo Login
    validar phone 10 digitos
    como validar el email esta en el modelo Login
    ningun campo debe ser vacio
*/