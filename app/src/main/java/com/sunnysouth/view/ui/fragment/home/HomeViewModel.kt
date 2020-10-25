package com.sunnysouth.view.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Calle 17a x 18 y 20"
    }
    val text: LiveData<String> = _text
}