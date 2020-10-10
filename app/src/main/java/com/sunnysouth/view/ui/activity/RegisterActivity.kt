package com.sunnysouth.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sunnysouth.R
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.presenter.IRegisterPresenter
import com.sunnysouth.presenter.RegisterPresenter
import com.sunnysouth.view.ui.fragment.register.CredentialsDataFragment
import com.sunnysouth.view.ui.fragment.register.PasswordDataFragment
import com.sunnysouth.view.ui.fragment.register.PersonalDataFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_credentials_data.*
import kotlinx.android.synthetic.main.fragment_password_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.*

class RegisterActivity : AppCompatActivity(){

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
            user.firstName = first_name?.editText?.text.toString()

            /*first_name.addOnEditTextAttachedListener {
                // If any specific changes should be done when the edit text is attached (and
                // thus when the trailing icon is added to it), set an
                // OnEditTextAttachedListener.

                // Example: The clear text icon's visibility behavior depends on whether the
                // EditText has input present. Therefore, an OnEditTextAttachedListener is set
                // so things like editText.getText() can be called.
            }
            */
            user.lastName = last_name?.editText?.text.toString()
            user.phoneNumber = phone_number?.editText?.text.toString()

            //Toast.makeText(this,user.first_name,Toast.LENGTH_LONG).show()

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

            user.username = editName?.editText?.text.toString()
            user.email = email?.editText?.text.toString()

            if(user.credentialDataIsValid()){
                if (user.emailIsValid()){
                    formp4?.visibility = View.VISIBLE
                    formp2?.visibility = View.INVISIBLE
                    formp3?.visibility = View.INVISIBLE

                    val fragment_password_data = PasswordDataFragment.newInstance()
                    replaceFragment(fragment_password_data,"pwdFragment")
                } else
                    Toast.makeText(this,"the email is not formatted correctly ",Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this,"The username and email must have a valor",Toast.LENGTH_LONG).show()

        }

        //Click Envent 'Envio'
        formp4?.setOnClickListener{
            user.password = password_user?.editText?.text.toString()
            user.passwordConfirmation = c_password_user?.editText?.text.toString()

            if(user.passwordsDataIsValid()){
                if(user.passwordsAreEquals()){
                    if (!user.passwordIsOnlyNumeric()){
                        //init
                        presenter = RegisterPresenter(this)
                        //Api Register
                        presenter.onRegister(user)
                    } else
                        Toast.makeText(this,"The password should not be just numbers",Toast.LENGTH_LONG).show()
                } else
                    Toast.makeText(this,"The passwords aren't equals",Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this,"The passwords must have a valor and higher or equal than 8 characters",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {

        val formp4 = findViewById(R.id.go_to_register_form_p4) as? MaterialButton
        val formp3 = findViewById(R.id.go_to_register_form_p3) as? MaterialButton
        val formp2 = findViewById(R.id.go_to_register_form_p2) as? MaterialButton

        val fragmentCount = supportFragmentManager.backStackEntryCount

        if(fragmentCount>1){
            when(fragmentCount){
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
        } else if(fragmentCount === 1){
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun  replaceFragment(fragment:Fragment, tag:String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun onRegisterSuccess(registerSuccess: RegisterSuccess?) {
        startHome()
    }

    fun onRegisterError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun startHome() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
