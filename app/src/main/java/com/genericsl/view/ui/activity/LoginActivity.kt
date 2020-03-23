package com.genericsl.view.ui.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.genericsl.R
import com.genericsl.presenter.ILoginPresenter
import com.genericsl.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILoginView {

    override fun onLoginSuccess(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onLoginError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


    internal  lateinit var loginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        loginPresenter = LoginPresenter(this)

        //event
        email_sign_in_button.setOnClickListener {
            loginPresenter.onLogin(email.text.toString(), password.text.toString())
        }
    }
}
