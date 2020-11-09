package com.sunnysouth.view.ui.activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sunnysouth.R
import com.sunnysouth.repository.models.Login
import com.sunnysouth.repository.models.LoginSuccess
import com.sunnysouth.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setContextApp(this)
        val sharedPref = this.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val token: String? = sharedPref.getString("token", null)

        if (token !== null) {
            startHome()
        }

        setContentView(R.layout.activity_login)
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar)

        email_sign_in_button.setOnClickListener {

            progressBar!!.visibility = View.VISIBLE
            val email = email?.editText?.text.toString()
            val password = editLastName?.editText?.text.toString()
            val login = Login(email, password)
            viewModel.login(login)
        }

        signup_action.setOnClickListener{
            startRegister()
        }

        viewModel.authenticationState.observe(this, Observer<LoginViewModel.AuthenticationState> {

            if (it === LoginViewModel.AuthenticationState.AUTHENTICATED){
                loginSuccess(viewModel.loginSuccess.value)
            }else if (it === LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION){
                loginError(viewModel.loginError.value)
            }
        })

    }

    private fun loginSuccess(login: LoginSuccess?) {
        progressBar!!.visibility = View.INVISIBLE
        Toast.makeText(this,login?.access_token,Toast.LENGTH_LONG).show()
        startHome()
    }

    private fun loginError(message: String?) {
        progressBar!!.visibility = View.INVISIBLE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun startHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startRegister(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
