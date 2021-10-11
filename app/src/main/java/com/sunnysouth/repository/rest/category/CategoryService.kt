package com.sunnysouth.repository.rest.category

import com.sunnysouth.repository.models.Category
import com.sunnysouth.repository.models.CategoryPage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CategoryService {
    @Multipart
    @PATCH("categories/{id}/")
    fun uploadPicture(@Header("Authorization") token: String, @Path("id") id: Int, @Part picture: MultipartBody.Part): Call<Category>

    @GET("categories/")
    fun getTopCategories(): Call<CategoryPage>
}