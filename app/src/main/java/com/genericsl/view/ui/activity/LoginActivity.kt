package com.genericsl.view.ui.activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.genericsl.R
import com.genericsl.interactor.models.Login
import com.genericsl.interactor.models.LoginSuccess
import com.genericsl.presenter.ILoginPresenter
import com.genericsl.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class LoginActivity : AppCompatActivity(), ILoginView, KoinComponent {

    lateinit var presenter: ILoginPresenter
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences("user_credentials" , Context.MODE_PRIVATE)
        val getToken:String? = sharedPref.getString("token", null)

        if (getToken !== null) {
            startHome()
        }

        setContentView(R.layout.activity_login)
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar
        presenter = LoginPresenter(this)



        email_sign_in_button.setOnClickListener {

            progressBar!!.visibility = View.VISIBLE
            val email = email.text.toString()
            val password = password.text.toString()
            val login = Login(email, password)

            presenter.onLogin(login, this)

        }
    }


    override fun onLoginSuccess(login: LoginSuccess?) {
        progressBar!!.visibility = View.INVISIBLE
        Toast.makeText(this,login?.access_token,Toast.LENGTH_LONG).show()
        presenter.saveDataLogin(login,this)
        startHome()
    }


    override fun onLoginError(message: String) {
        progressBar!!.visibility = View.INVISIBLE
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun startHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
