package com.sunnysouth.repository.rest.category

import android.content.Context
import com.sunnysouth.R
import com.sunnysouth.config.BASE_URL
import com.sunnysouth.repository.models.CategoryPage
import com.sunnysouth.repository.rest.RetrofitClient
import com.sunnysouth.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository(private val viewModel: HomeViewModel) {

    fun getTopCategories(context: Context){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<CategoryService>(CategoryService::class.java)

        service?.getTopCategories()?.enqueue(object: Callback<CategoryPage> {

            override fun onFailure(call: Call<CategoryPage>, t: Throwable) {
                //viewModel.setUserError(context.getString(R.string.error_in_ready_user))
            }

            override fun onResponse(call: Call<CategoryPage>, response: Response<CategoryPage>) {
                if(response.code() == 200){
                    val user = response.body()
                    user?.let {
                        viewModel.categoryPage.value = it
                    }
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(R.string.error_data_user)
                    //viewModel.setUserError(messageError)
                }
            }
        })
    }
}