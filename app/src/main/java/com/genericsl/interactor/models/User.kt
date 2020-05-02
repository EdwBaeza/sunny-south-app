package com.genericsl.interactor.models

data class User (
    var username: String,
    var first_name: String,
    var last_name: String,
    var phone_number: String,
    var email: String,
    var password: String,
    var password_confirmation: String
)