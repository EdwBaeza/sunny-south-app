package com.genericsl.model.clientRest

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(url: String): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}