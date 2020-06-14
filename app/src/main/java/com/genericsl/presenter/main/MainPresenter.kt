package com.genericsl.presenter.main

import android.content.Context
import com.genericsl.interactor.clientRest.main.MainServiceImp
import com.genericsl.view.ui.activity.main.IMainView

class MainPresenter (val view: IMainView): IMainPresenter {

    val interactor: MainServiceImp = MainServiceImp(this)

    override fun signOff(context: Context){
        interactor.signOff(context)
    }

}