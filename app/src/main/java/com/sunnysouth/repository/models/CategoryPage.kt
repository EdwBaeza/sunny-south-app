package com.sunnysouth.repository.models

import com.google.gson.annotations.SerializedName

class CategoryPage {
    @SerializedName("count")
    var count: Int = 0

    @SerializedName("next")
    var next: String = ""

    @SerializedName("previous")
    var previous: String = ""

    @SerializedName("results")
    var categories: List<Category> = ArrayList<Category>()
}
