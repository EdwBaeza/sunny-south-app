package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName

class Category {

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("picture")
    lateinit var picture: String
}