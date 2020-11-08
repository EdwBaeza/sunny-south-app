package com.sunnysouth.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sunnysouth.R
import com.sunnysouth.repository.models.RegisterSuccess
import com.sunnysouth.repository.models.User
import com.sunnysouth.view.ui.fragments.registers.CredentialsDataFragment
import com.sunnysouth.view.ui.fragments.registers.PasswordDataFragment
import com.sunnysouth.view.ui.fragments.registers.PersonalDataFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunnysouth.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_credentials_data.*
import kotlinx.android.synthetic.main.fragment_password_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RegisterActivity : AppCompatActivity(){

    val viewModel: RegisterViewModel by viewModels()
    lateinit var formp4:MaterialButton
    lateinit var formp3:MaterialButton
    lateinit var formp2:MaterialButton
    var fragment_personal_data = PersonalDataFragment.newInstance()
    lateinit var fragment_credential_data:Fragment
    lateinit var fragment_password_data:Fragment

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

        replaceFragment(fragment_personal_data, "personalFragment")

        //Click Envent 'Siguiente#1'
        formp2?.setOnClickListener{
            //get the text
            var firstName = findViewById<EditText>(R.id.first_name)
            var lastName = findViewById<EditText>(R.id.last_name)
            var phoneNumber = findViewById<EditText>(R.id.phone_number)

            viewModel?.setUserPersonalData(firstName?.text.toString(), lastName?.text.toString(), phoneNumber?.text.toString())

            viewModel.user.observe(
                this,
                Observer<User> {
                    var userObj = it
                    if(it.IsValidpersonalData())
                    {
                        formp3?.visibility = View.VISIBLE
                        formp2?.visibility = View.INVISIBLE

                        fragment_credential_data = CredentialsDataFragment.newInstance()
                        replaceFragment(fragment_credential_data, "credentialsFragment")
                    }
                    else
                        this.onUserError(it.userErrors, "Error con los datos personales")
                })

        }

        //Click Envent 'Siguiente#2'
        formp3?.setOnClickListener{

            var userName = findViewById<EditText>(R.id.editName)
            var email = findViewById<EditText>(R.id.email)

            viewModel?.setUserCredentialsData(userName?.text.toString(), email?.text.toString())

            viewModel.user.observe(
                this,
                Observer<User> {
                    if(it.IsValidcredentialData()){
                        formp4?.visibility = View.VISIBLE
                        formp2?.visibility = View.INVISIBLE
                        formp3?.visibility = View.INVISIBLE

                        fragment_password_data = PasswordDataFragment.newInstance()
                        replaceFragment(fragment_password_data, "pwdFragment")
                    } else
                        this.onUserError(it.userErrors, "Error con el usuario o email")
                })

        }

        //Click Envent 'Envio'
        formp4?.setOnClickListener{
            formp4.isEnabled = false

            var password = findViewById<EditText>(R.id.password_user)
            var cPassword = findViewById<EditText>(R.id.c_password_user)

            viewModel?.setUserPassWordData(password?.text.toString(), cPassword?.text.toString())

            viewModel.user.observe(
                this,
                Observer<User> { user ->
                    if(user.IsValidpasswordsData()){
                        viewModel.onRegister(user)

                        viewModel.authenticationState.observe(
                            this,
                            Observer<RegisterViewModel.RegisterState> {

                                if (it === RegisterViewModel.RegisterState.OK) {
                                    onRegisterSuccess(viewModel.registerSuccess.value)
                                } else if (it === RegisterViewModel.RegisterState.INVALID) {
                                    formp4.isEnabled = true
                                    onRegisterError(viewModel.registerError.value, user)
                                    viewModel.clear()
                                }
                            })
                    } else
                    {
                        formp4.isEnabled = true
                        this.onUserError(user.userErrors, "Error con las contraseñas")
                    }
                })
        }
    }

    override fun onBackPressed() {
        //val fragmentCount = supportFragmentManager.backStackEntryCount

        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainer);

        when(currentFragment?.tag)
        {
            "credentialsFragment" ->{
                formp3?.visibility = View.INVISIBLE
                formp2?.visibility = View.VISIBLE
                replaceFragment(fragment_personal_data, "personalFragment")
            }
            "pwdFragment"->{
                formp4?.visibility = View.INVISIBLE
                formp3?.visibility = View.VISIBLE
                replaceFragment(fragment_credential_data, "credentialsFragment")
            }
            "personalFragment"->{
                finish()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        /*if(fragmentCount>1){
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
        }*/
    }

    private fun  replaceFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun onRegisterSuccess(registerSuccess: RegisterSuccess?) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Verifica tu correo electronico")
            .setMessage("Hola ${first_name?.text.toString()} verifica tu cuenta para tener acceso a todo lo que puede ofrecer la app")
            .setPositiveButton("Regresar") { dialog, which ->
                // Respond to positive button press
                startLogin()
            }
            .setCancelable(false)
            .show()
    }

    private fun onRegisterError(messages: MutableList<String>?, user: User) {

        val items = messages?.toTypedArray()

        //Dialog del error
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setItems(items) { dialog, index ->
                // Respond to item chosen
                var valor = items?.get(index)

                if (valor?.contains("username")!! || valor?.contains("email")!!)
                {
                    formp4?.visibility = View.INVISIBLE
                    formp2?.visibility = View.INVISIBLE
                    formp3?.visibility = View.VISIBLE

                    replaceFragment(fragment_credential_data, "credentialsFragment")
                }
            }
            .setNegativeButton("Cancelar"){ dialog, which ->
                startLogin()
            }
            .setCancelable(false)
            .show()
    }

    private fun onUserError(messages: MutableList<String>?, title: String) {

        val items = messages?.toTypedArray()
        //Dialog del error
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setItems(items, null)
            .setPositiveButton("Ok", null)
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
