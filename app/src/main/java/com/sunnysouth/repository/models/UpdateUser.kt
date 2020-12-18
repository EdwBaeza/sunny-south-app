package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName

class UpdateUser(
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("phone_number")
    var phoneNumber: String
)