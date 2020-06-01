package com.genericsl.interactor.clientRest.login

import android.content.Context
import com.genericsl.config.Config
import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.presenter.ILoginPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.genericsl.R

class LoginServiceImp(val presenter: ILoginPresenter) {

    fun onLogin(login: Login, context: Context){

        val retrofit = RetrofitClient().getClient(Config.BASE_URL)
        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.goLogin(login)?.enqueue(object : Callback<LoginSuccess>{
            override fun onFailure(call: Call<LoginSuccess>, t: Throwable) {
                presenter.onLoginError(context.getString(R.string.error_in_login))
            }

            override fun onResponse(call: Call<LoginSuccess>, response: Response<LoginSuccess>) {

                if(response.code() != 200){
                    presenter.onLoginError(context.getString(R.string.error_data_user))
                }else{
                    val loginSuccess: LoginSuccess? = response.body()
                    presenter.onLoginSuccess(loginSuccess)
                }
            }
        })
    }

    fun saveDataLogin(login: LoginSuccess?, context: Context) {
        val sharedPref = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("token", login?.access_token)
        editor.putString("email", login?.user?.email)
        editor.apply()
    }
}