package com.genericsl.view.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.genericsl.R
import com.genericsl.interactor.models.RegisterSuccess
import com.genericsl.interactor.models.User
import com.genericsl.presenter.IRegisterPresenter
import com.genericsl.presenter.RegisterPresenter
import com.genericsl.view.ui.activity.login.LoginActivity
import com.genericsl.view.ui.activity.main.MainActivity
import com.genericsl.view.ui.fragment.register.CredentialsDataFragment
import com.genericsl.view.ui.fragment.register.PasswordDataFragment
import com.genericsl.view.ui.fragment.register.PersonalDataFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_credentials_data.*
import kotlinx.android.synthetic.main.fragment_password_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.*

class RegisterActivity : AppCompatActivity() ,
    IRegisterView {

    lateinit var presenter: IRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView
        setContentView(R.layout.activity_register)

        //InvisibleButtons
        val formp4 = findViewById(R.id.go_to_register_form_p4) as? MaterialButton
        formp4?.visibility = View.INVISIBLE

        val formp3 = findViewById(R.id.go_to_register_form_p3) as? MaterialButton
        formp3?.visibility = View.INVISIBLE

        val fragment_personal_data = PersonalDataFragment.newInstance()
        replaceFragment(fragment_personal_data,"personalFragment")

        val formp2 = findViewById(R.id.go_to_register_form_p2) as? MaterialButton

        var user = User()

        //Click Envent 'Siguiente#1'
        formp2?.setOnClickListener{
            //get the text
            user.first_name = first_name.text.toString()
            user.last_name = last_name.text.toString()
            user.phone_number = phone_number.text.toString()

            if(user.personalDataIsValid())
            {
                if(user.phoneNumberValidation())
                {
                    formp3?.visibility = View.VISIBLE
                    formp2?.visibility = View.INVISIBLE

                    val fragment_credential_data = CredentialsDataFragment.newInstance()
                    replaceFragment(fragment_credential_data,"credentialsFragment")
                }
                else
                    Toast.makeText(this,"The phone number must be equal to 10",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"The first and last name must have a valor",Toast.LENGTH_LONG).show()
        }

        //Click Envent 'Siguiente#2'
        formp3?.setOnClickListener{

            user.username = username.text.toString()
            user.email = email.text.toString()

            if(user.credentialDataIsValid()){
                if (user.emailIsValid())
                {
                    formp4?.visibility = View.VISIBLE
                    formp2?.visibility = View.INVISIBLE
                    formp3?.visibility = View.INVISIBLE

                    val fragment_password_data = PasswordDataFragment.newInstance()
                    replaceFragment(fragment_password_data,"pwdFragment")
                }
                else
                    Toast.makeText(this,"the email is not formatted correctly ",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"The username and email must have a valor",Toast.LENGTH_LONG).show()

        }

        //Click Envent 'Envio'
        formp4?.setOnClickListener{

            user.password = password_user.text.toString()
            user.password_confirmation = c_password_user.text.toString()

            if(user.passwordsDataIsValid())
            {
                if(user.thePasswordsAreEquals()){
                    if (!user.passwordIsOnlyNumeric())
                    {
                        //init
                        presenter = RegisterPresenter(this)
                        //Api Register
                        presenter.onRegister(user)
                    }
                    else
                        Toast.makeText(this,"The password should not be just numbers",Toast.LENGTH_LONG).show()
                }
                else
                    Toast.makeText(this,"The passwords aren't equals",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"The passwords must have a valor and higher or equal than 8 characters",Toast.LENGTH_LONG).show()

            //Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show()
        }

        //Toast.makeText(this,""+ formp4, Toast.LENGTH_LONG).show()
    }

    fun registerRequest(user:User):String
    {
        return "";
    }

    override fun onBackPressed() {

        val formp4 = findViewById(R.id.go_to_register_form_p4) as? MaterialButton
        val formp3 = findViewById(R.id.go_to_register_form_p3) as? MaterialButton
        val formp2 = findViewById(R.id.go_to_register_form_p2) as? MaterialButton

        val fragmentCount = supportFragmentManager.backStackEntryCount

        if(fragmentCount>1){

            when(fragmentCount)
            {
                3 -> {
                    formp4?.visibility = View.INVISIBLE
                    formp3?.visibility = View.VISIBLE
                }

                2 -> {
                    formp3?.visibility = View.INVISIBLE
                    formp2?.visibility = View.VISIBLE
                }
            }
            super.onBackPressed()
        }
        else if(fragmentCount.equals(1)){
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Toast.makeText(this,"Estoy en el RegisterActivity",Toast.LENGTH_LONG).show()
        //super.onBackPressed()
    }

    private fun  replaceFragment(fragment:Fragment, tag:String)
    {
        val fragmentTranscation = supportFragmentManager.beginTransaction()
        //val currentFragment: Fragment = supportFragmentManager.findFragmentByTag(tag) as Fragment
        fragmentTranscation.replace(R.id.fragmentContainer, fragment)
        //fragmentTranscation.addToBackStack(null)
        /*if (fragment.isAdded) {
            fragmentTranscation
                .hide(currentFragment)
                .show(fragment)
        } else {
            fragmentTranscation
                .hide(currentFragment)
                .add(R.id.container, fragment, tag)
        }*/
        fragmentTranscation.addToBackStack(null)
        fragmentTranscation.commit()
    }

    override fun onRegisterSuccess(registerSuccess: RegisterSuccess?) {
        startHome()
    }

    override fun onRegisterError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun startHome() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
