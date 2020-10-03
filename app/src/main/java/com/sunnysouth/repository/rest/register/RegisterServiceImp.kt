package com.sunnysouth.repository.rest.register

import com.sunnysouth.config.BASE_URL
import com.sunnysouth.repository.rest.RetrofitClient
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.presenter.IRegisterPresenter
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterServiceImp(val presenter:IRegisterPresenter) {

    fun getRegister(user: User){

        val retrofit = RetrofitClient().getClient(BASE_URL)
        val service = retrofit?.create<RegisterService>(RegisterService::class.java)

        service?.goRegister(user)?.enqueue(object : Callback<RegisterSuccess>{
            override fun onFailure(call: Call<RegisterSuccess>, t: Throwable) {
                presenter.onRegisterError("Register error")
            }

            override fun onResponse(call: Call<RegisterSuccess>, response: Response<RegisterSuccess>) {

                if(response.code() != 200 && response.code() != 201){
                    var message = ""
                    val jObjError = JSONObject(response.errorBody()?.string())
                    jObjError.keys().forEach {
                        val errors: JSONArray = jObjError.getJSONArray(it)
                        if (errors.length() == 1){
                            message = errors[0].toString()
                        }
                        else {
                            for (i in 0 until errors.length()) {
                                message += " ${errors[i]}"
                            }
                        }
                    }
                    presenter.onRegisterError(message)
                }else{
                    val registerSuccess: RegisterSuccess? = response.body()
                    presenter.onRegisterSuccess(registerSuccess)
                }
            }
        })
    }
}