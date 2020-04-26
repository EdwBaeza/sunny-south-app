package com.genericsl.view.ui.activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.genericsl.R
import com.genericsl.model.Login
import com.genericsl.model.clientRest.ApiService
import com.genericsl.presenter.ILoginPresenter
import com.genericsl.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity(), ILoginView {

    override fun onLoginSuccess(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onLoginError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    internal  lateinit var loginPresenter: ILoginPresenter


    //progressBar
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

        //init
        loginPresenter = LoginPresenter(this)

        //event
        email_sign_in_button.setOnClickListener {
            // loginPresenter.onLogin(email.text.toString(), password.text.toString())

            progressBar!!.visibility = View.VISIBLE
            peticionAPi()


        }

    }

    fun peticionAPi(){
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://g2y45q7yh6.execute-api.us-west-2.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ApiService>(ApiService::class.java)

        service.goLogin(Login(email.text.toString(), password.text.toString(),"1")).enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                var auth : Login? = response.body()

                if (auth == null)
                {
                    progressBar!!.visibility = View.INVISIBLE
                    Toast.makeText(this@LoginActivity,""+response.message(),Toast.LENGTH_LONG).show()

                }
                else{
                    //Toast.makeText(this@LoginActivity,""+auth?.access_token,Toast.LENGTH_LONG).show()
                    val sharedPref = this@LoginActivity.getSharedPreferences("credenciales",Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("token", auth.access_token)
                    editor.putString("email", email.text.toString())
                    editor.apply()


                    val intento1 = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intento1)
                    progressBar!!.visibility = View.INVISIBLE
                }


            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                progressBar!!.visibility = View.INVISIBLE
                Toast.makeText(this@LoginActivity,"NO EXISTE CONEXIÓN",Toast.LENGTH_LONG).show()
            }
        })

    }

}
