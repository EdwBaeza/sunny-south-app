package com.sunnysouth.view.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunnysouth.R
import com.sunnysouth.repository.models.User
import com.sunnysouth.viewmodel.UserProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView


class UserProfileFragment : Fragment() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        this.activity?.let { profileViewModel.setContextApp(it) }

        this.root = inflater.inflate(R.layout.fragment_profile, container, false)
        val circleImageProfile: CircleImageView = root.findViewById(R.id.imageProfile)
        val btnEditNameProfile : ImageButton = root.findViewById(R.id.btnEditNameProfile)
        val btnEditLastNameProfile : ImageButton = root.findViewById(R.id.btnEditLastNameProfile)
        val btnEditPhoto : ImageButton = root.findViewById(R.id.btnEditPhoto)
        val txtNameProfile : TextView = root.findViewById(R.id.txtNameProfile)
        val txtLastNameProfile : TextView = root.findViewById(R.id.txtLastNameProfile)
        val txtUsername : TextView = root.findViewById(R.id.txtUsername)
        val txtPhone : TextView = root.findViewById(R.id.txtPhone)
        val txtEmail : TextView = root.findViewById(R.id.txtEmail)
        val txtPassword : TextView = root.findViewById(R.id.txtPassword)

        profileViewModel.user.observe(this, Observer {
            txtUsername.text = it.username
            txtNameProfile.text = it.firstName
            txtLastNameProfile.text = it.lastName
            txtEmail.text = it.email
            txtPhone.text = it.phoneNumber
            txtPassword.text = "panfilo00"
        })

        profileViewModel.getSessionUser()

        /*Picasso.with(context)
            .load("https://www.lavanguardia.com/r/GODO/LV/p7/WebSite/2020/04/14/Recortada/img_psola_20180724-165630_imagenes_lv_terceros_elite_101_unit_0049_r-kCsG-U48496101636McD-992x558@LaVanguardia-Web.jpg")
            .placeholder(R.drawable.fondo_mexico)
            .error(R.drawable.fondo_mexico)
            .into(circleImageProfile)*/

        btnEditNameProfile.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity!!.layoutInflater
            val v: View = inflater.inflate(R.layout.fragment_edit_first_name_profile, null)

            val editName : EditText = v.findViewById(R.id.editName)
            editName.setText(txtNameProfile.text)

            val btnActualizeFirstName : Button = v.findViewById(R.id.btnActualizeFirstName)

            builder.setView(v)
            val dialog: AlertDialog = builder.create()
            dialog.show()

            btnActualizeFirstName.setOnClickListener {
                txtNameProfile.text = editName.text
                dialog.cancel()
            }
        }

        btnEditLastNameProfile.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity!!.layoutInflater
            val v: View = inflater.inflate(R.layout.fragment_edit_last_name_profile, null)

            val editLastName : EditText = v.findViewById(R.id.editLastName)
            editLastName.setText(txtLastNameProfile.text)

            val btnActualizeLastName : Button = v.findViewById(R.id.btnActualizeLastName)

            builder.setView(v)
            val dialog: AlertDialog = builder.create()
            dialog.show()

            btnActualizeLastName.setOnClickListener {
                txtLastNameProfile.text = editLastName.text
                dialog.cancel()
            }
        }

        btnEditPhoto.setOnClickListener {
            openGallery()
        }
        return root
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 123){
            val circleImageProfile: CircleImageView = root.findViewById(R.id.imageProfile)
            circleImageProfile.setImageURI(data?.data)
        }
    }
}