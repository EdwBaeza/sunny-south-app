package com.genericsl.interactor.clientRest.main

import android.content.Context
import com.genericsl.presenter.main.IMainPresenter

class MainServiceImp(val presenter: IMainPresenter) {

    fun signOff(context: Context){
        val sharedPref = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}