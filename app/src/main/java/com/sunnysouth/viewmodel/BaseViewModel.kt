package com.sunnysouth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel

open class BaseViewModel(): ViewModel() {

    lateinit var context: Context

    protected fun getUserPreferences(): Array<String> {
        val sharedPref = this.context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "").toString()
        val token = sharedPref.getString("token", "").toString()
        val tokenFormatted = "Bearer $token"

        if (username.isNullOrEmpty() || token.isNullOrEmpty())
            throw IllegalArgumentException("User and token required")

        return arrayOf(tokenFormatted, username)
    }
}