package com.genericsl.view.ui.activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.genericsl.R
import com.genericsl.interactor.clientRest.RetrofitClient
import com.genericsl.interactor.clientRest.login.LoginService
import com.genericsl.interactor.clientRest.login.LoginServiceImp
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.interactor.models.User
import com.genericsl.presenter.ILoginPresenter
import com.genericsl.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            //loginPresenter.onLogin(email.text.toString(), password.text.toString())

            //progressBar!!.visibility = View.VISIBLE


            //LoginServiceImp().getLogin(email.text.toString(),password.text.toString())
            //Toast.makeText(this@LoginActivity,"mensage: "+ mensa,Toast.LENGTH_LONG).show()

            peticionAPi(email.text.toString(),password.text.toString())


        }

    }

    fun peticionAPi(email:String, password:String){

        val retrofit = RetrofitClient().getClient("https://ec2-54-187-153-186.us-west-2.compute.amazonaws.com/")

        val service = retrofit?.create<LoginService>(LoginService::class.java)

        service?.goLogin(
            Login(
                email,
                password
            )
        )?.enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                var auth : Login? = response.body()

                if (auth == null)
                {
                    progressBar!!.visibility = View.INVISIBLE
                    Toast.makeText(this@LoginActivity,""+response.code(),Toast.LENGTH_LONG).show()

                }
                else{
                    //Toast.makeText(this@LoginActivity,""+auth?.access_token,Toast.LENGTH_LONG).show()
                    /*val sharedPref = this@LoginActivity.getSharedPreferences("credenciales",Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("token", auth.access_token)
                    editor.putString("email", email.text.toString())
                    editor.apply()*/

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
