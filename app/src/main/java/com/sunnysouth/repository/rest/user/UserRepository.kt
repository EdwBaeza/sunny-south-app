package com.sunnysouth.repository.rest.user

import android.content.Context
import android.util.Log
import com.sunnysouth.R
import com.sunnysouth.config.BASE_URL
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.rest.RetrofitClient
import com.sunnysouth.viewmodel.UserProfileViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val viewModel: UserProfileViewModel){

    fun findByUsername(token: String, username: String, context: Context){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<UserService>(UserService::class.java)

        service?.findByUsername(token,username)?.enqueue(object: Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                viewModel.setUserError(context.getString(R.string.error_in_ready_user))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() == 200){
                    val user = response.body()
                    user?.let {
                        viewModel.setSessionUser(it)
                    }
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(R.string.error_data_user)
                    viewModel.setUserError(messageError)
                }
            }
        })
    }

    fun getCurrentUser(token: String, context: Context){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<UserService>(UserService::class.java)

        service?.getCurrentUser(token)?.enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                viewModel.setUserError(context.getString(R.string.error_in_ready_user))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() == 200){
                    val user = response.body()
                    user?.let {
                        viewModel.setSessionUser(it)
                    }
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(R.string.error_data_user)
                    viewModel.setUserError(messageError)
                }
            }
        })
    }

    fun update(token: String, username: String, user: User, context: Context) {

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<UserService>(UserService::class.java)

        service?.update(token, username, user)?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                viewModel.setUserError(context.getString(R.string.error_in_update_user))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() === 200){
                    val user = response.body()
                    user.let {
                        if (it != null) {
                            viewModel.setSessionUser(it)
                        }
                    }
                    Log.i("Error test", "prueba")
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(
                        R.string.error_data_user)
                    viewModel.setUserError(messageError)
                }
            }

        })
    }

    fun uploadPhoto(token: String, username: String, picture: MultipartBody.Part, context: Context) {

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<UserService>(UserService::class.java)

        service?.uploadPhoto(token, username, picture)?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                viewModel.setUserError(context.getString(R.string.error_in_update_user))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code() === 200){
                    val user = response.body()
                    user.let {
                        if (it != null) {
                            viewModel.setSessionUser(it)
                        }
                    }
                    Log.i("Error test", "prueba")
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