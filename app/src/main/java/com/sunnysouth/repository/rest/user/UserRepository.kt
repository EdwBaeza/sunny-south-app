package com.sunnysouth.repository.rest.user

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunnysouth.R
import com.sunnysouth.config.BASE_URL
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.RetrofitClient
import com.sunnysouth.viewmodel.UserProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val viewModel: UserProfileViewModel){

    fun getUser(token: String, username: String, context: Context){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<UserService>(UserService::class.java)

        service?.getUser(token,username)?.enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                viewModel.setUserError(context.getString(R.string.error_in_login))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() === 200){
                    val user = response.body()
                    user?.let {
                        viewModel.setSessionUser(it)
                    }
                    //Log.i("Error test", "prueba")
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(
                        R.string.error_data_user)
                    viewModel.setUserError(messageError)
                }
            }
        })
    }
}