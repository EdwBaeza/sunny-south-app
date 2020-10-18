package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

class UserSucess(
    var profile : Profile,
    @SerializedName("last_login")
    var lastLogin: String? = null,
    @SerializedName("username")
    var username: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("date_joined")
    var dateJoined: SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    @SerializedName("uuid")
    var uuid: String,
    @SerializedName("created")
    var created: SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    @SerializedName("modified")
    var modified: SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    @SerializedName("email")
    var email: String,
    @SerializedName("phone_number")
    var phoneNumber: String
)