package com.sunnysouth.repository.rest.login

import android.content.Context
import com.sunnysouth.config.BASE_URL
import com.sunnysouth.repository.rest.RetrofitClient
import com.sunnysouth.repository.models.Login
import com.sunnysouth.repository.models.LoginSuccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.sunnysouth.R
import com.sunnysouth.viewmodel.LoginViewModel

class LoginRepository(private val viewModel: LoginViewModel) {


    fun login(loginData: Login, context: Context){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.login(loginData)?.enqueue(object : Callback<LoginSuccess>{

            override fun onFailure(call: Call<LoginSuccess>, t: Throwable) {
                viewModel.setLoginError(context.getString(R.string.error_in_login))
            }

            override fun onResponse(call: Call<LoginSuccess>, response: Response<LoginSuccess>) {
                if(response.code() === 200){
                    val loginSuccess: LoginSuccess? = response.body()
                    viewModel.setLoginSuccess(loginSuccess)
                }else{
                    val errorBody = response.errorBody()
                    val messageError =  if(errorBody != null) errorBody.string() else context.getString(R.string.error_data_user)
                    viewModel.setLoginError(messageError)
                }
            }
        })
    }
}

