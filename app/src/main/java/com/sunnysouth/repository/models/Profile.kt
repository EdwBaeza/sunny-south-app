package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

class Profile {
    @SerializedName("user")
    lateinit var userProfile: String
    @SerializedName("uuid")
    lateinit var uuid: String
    @SerializedName("created")
    var created =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("modified")
    var modified =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @SerializedName("slug_name")
    lateinit var slugName: String
    @SerializedName("biography")
    lateinit var biography: String
    @SerializedName("location")
    var location: String? = null
    @SerializedName("picture")
    var picture: String? = null
    @SerializedName("is_client")
    var isClient: Boolean = true
    @SerializedName("is_supplier")
    var isSupplier: Boolean = true
    @SerializedName("reputation")
    var reputation: Double = 0.0
}