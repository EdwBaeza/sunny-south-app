package com.sunnysouth.view.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunnysouth.repository.models.Category
import com.sunnysouth.repository.models.CategoryPage
import com.sunnysouth.repository.rest.category.CategoryRepository
import com.sunnysouth.repository.rest.user.UserRepository
import com.sunnysouth.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel() {

    var categoryPage = MutableLiveData<CategoryPage>()
    var repository = CategoryRepository(this)

    fun getCategories(){
        val (token, _) = getUserPreferences()
        repository.getTopCategories(token, this.context)
    }

}