package com.sunnysouth.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sunnysouth.R
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.view.ui.fragment.register.CredentialsDataFragment
import com.sunnysouth.view.ui.fragment.register.PasswordDataFragment
import com.sunnysouth.view.ui.fragment.register.PersonalDataFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunnysouth.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_credentials_data.*
import kotlinx.android.synthetic.main.fragment_password_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.*

class RegisterActivity : AppCompatActivity(){

    private val viewModel: RegisterViewModel by viewModels()
    lateinit var formp4:MaterialButton
    lateinit var formp3:MaterialButton
    lateinit var formp2:MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView
        viewModel.setContextApp(this)
        setContentView(R.layout.activity_register)

        this.formp4 = (findViewById(R.id.go_to_register_form_p4) as? MaterialButton)!!
        this.formp3 = (findViewById(R.id.go_to_register_form_p3) as? MaterialButton)!!
        this.formp2 = (findViewById(R.id.go_to_register_form_p2) as? MaterialButton)!!

        formp4?.visibility = View.INVISIBLE
        formp2?.visibility = View.VISIBLE
        formp3?.visibility = View.INVISIBLE

        val fragment_personal_data = PersonalDataFragment.newInstance()
        replaceFragment(fragment_personal_data,"personalFragment")

        var user = User()

        //Click Envent 'Siguiente#1'
        formp2?.setOnClickListener{
            //get the text
            user.firstName = first_name?.editText?.text.toString()
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
                        //viewModel = RegisterPresenter(this)
                        //Api Register
                        viewModel.onRegister(user)

                        viewModel.authenticationState.observe(this, Observer<RegisterViewModel.RegisterState> {

                            if (it === RegisterViewModel.RegisterState.OK){
                                onRegisterSuccess(viewModel.registerSuccess.value)
                            }else if (it === RegisterViewModel.RegisterState.INVALID){
                                onRegisterError(viewModel.registerError.value, user)
                            }
                        })

                        //onRegisterSuccess(viewModel.registerSuccess.value)

                    } else
                        Toast.makeText(this,"The password should not be just numbers",Toast.LENGTH_LONG).show()
                } else
                    Toast.makeText(this,"The passwords aren't equals",Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this,"The passwords must have a valor and higher or equal than 8 characters",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
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

    private fun onRegisterSuccess(registerSuccess: RegisterSuccess?) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Verifica tu correo electronico")
            .setMessage("Hola ${registerSuccess?.user?.fullName()} verifica tu cuenta para tener acceso a todo lo que puede ofrecer la app")
            .setPositiveButton("Regresar") { dialog, which ->
                // Respond to positive button press
                startLogin()
            }
            .setCancelable(false)
            .show()
        //startHome()
    }

    private fun onRegisterError(messages: MutableList<String>?, user:User) {

        val items = messages?.toTypedArray()

        // a qui comienza a volver a llenar el registro
        this.editName?.editText?.setText(user.username)
        this.email?.editText?.setText(user.email)
        this.first_name?.editText?.setText(user.firstName)
        this.last_name?.editText?.setText(user.lastName)
        this.phone_number?.editText?.setText(user.phoneNumber)
        this.password_user?.editText?.setText(user.password)
        this.c_password_user?.editText?.setText(user.passwordConfirmation)

        //Dialog del error
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setItems(items) { dialog, index ->
                // Respond to item chosen
                var valor = items?.get(index)

                if (valor?.contains("non_field")!!)
                {
                    val fragment_credential_data = CredentialsDataFragment.newInstance()
                    replaceFragment(fragment_credential_data,"credentialsFragment")

                    formp4?.visibility = View.VISIBLE
                    formp2?.visibility = View.INVISIBLE
                    formp3?.visibility = View.INVISIBLE
                }

                if (valor?.contains("username")!!)
                {
                    formp4?.visibility = View.INVISIBLE
                    formp2?.visibility = View.INVISIBLE
                    formp3?.visibility = View.VISIBLE

                    val fragment_credential_data = CredentialsDataFragment.newInstance()
                    replaceFragment(fragment_credential_data,"credentialsFragment")
                }
            }
            .setNegativeButton("Cancelar"){dialog, which ->
                startLogin()
            }
            .setCancelable(false)
            .show()
        //Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun startHome() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
