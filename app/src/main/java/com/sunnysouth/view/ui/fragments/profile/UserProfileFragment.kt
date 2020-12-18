package com.sunnysouth.view.ui.fragments.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.sunnysouth.R
import com.sunnysouth.repository.models.UpdateUser
import com.sunnysouth.viewmodel.UserProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UserProfileFragment : Fragment() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var root: View
    var newFirstName: String = ""
    var newLastName: String = ""
    var newPhoneNumber: String = ""
    lateinit var uri: Uri
    var photoCondition: Boolean = false
    private val codePermission = 777
    private val codeGallery = 123

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
        val btnEditPhoneNumberProfile : ImageButton = root.findViewById(R.id.btnEditPhoneNumberProfile)
        val btnEditPhoto : ImageButton = root.findViewById(R.id.btnEditPhoto)
        val btnSaveData : Button = root.findViewById(R.id.btnSaveData)
        val txtNameProfile : TextView = root.findViewById(R.id.txtFirstNameProfile)
        val txtLastNameProfile : TextView = root.findViewById(R.id.txtLastNameProfile)
        val txtUsernameProfile : TextView = root.findViewById(R.id.txtUsernameProfile)
        val txtPhoneProfile : TextView = root.findViewById(R.id.txtPhoneProfile)
        val txtEmailProfile : TextView = root.findViewById(R.id.txtEmailProfile)
        val txtPasswordProfile : TextView = root.findViewById(R.id.txtPasswordProfile)

        profileViewModel.user.observe(this, Observer {
            txtUsernameProfile.text = it.username
            txtNameProfile.text = it.firstName
            txtLastNameProfile.text = it.lastName
            txtEmailProfile.text = it.email
            txtPhoneProfile.text = it.phoneNumber
            txtPasswordProfile.text = "panfilo00"

            if (it.profile.picture === ""){
                circleImageProfile.setImageResource(R.drawable.profile_default)
            }else{
                Picasso.with(context)
                    .load(it.profile.picture)
                    .placeholder(R.drawable.profile_default)
                    .error(R.drawable.profile_error)
                    .into(circleImageProfile)
            }
        })

        profileViewModel.getSessionUser()

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
                newFirstName = editName.text.toString()
                txtNameProfile.text = newFirstName
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
                newLastName = editLastName.text.toString()
                txtLastNameProfile.text = newLastName
                dialog.cancel()
            }
        }

        btnEditPhoneNumberProfile.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity!!.layoutInflater
            val v: View = inflater.inflate(R.layout.fragment_edit_phone_number_profile, null)

            val editPhoneNumber : EditText = v.findViewById(R.id.editPhoneNumber)
            editPhoneNumber.setText(txtPhoneProfile.text)

            val btnActualizePhoneNumber : Button = v.findViewById(R.id.btnActualizePhoneNumber)

            builder.setView(v)
            val dialog: AlertDialog = builder.create()
            dialog.show()

            btnActualizePhoneNumber.setOnClickListener {
                newPhoneNumber = editPhoneNumber.text.toString()
                txtPhoneProfile.text = newPhoneNumber
                dialog.cancel()
            }
        }

        btnSaveData.setOnClickListener {
            if (newFirstName==""){
                newFirstName = txtNameProfile.text.toString()
            }
            if (newLastName==""){
                newLastName = txtLastNameProfile.text.toString()
            }
            if (newPhoneNumber==""){
                newPhoneNumber = txtPhoneProfile.text.toString()
            }

            val updateUser = UpdateUser(newFirstName, newLastName, newPhoneNumber)
            profileViewModel.updateUser(updateUser)
            if(photoCondition){
                uploadPhotoGallery()
            }
            Toast.makeText(activity, "Datos actualizados", Toast.LENGTH_LONG).show()
        }

        btnEditPhoto.setOnClickListener {
            checkPermission()
        }
        return root
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, codeGallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == codeGallery){
            val circleImageProfile: CircleImageView = root.findViewById(R.id.imageProfile)
            uri = data?.data!!
            circleImageProfile.setImageURI(uri)
            photoCondition = true
        }
    }

    private fun uploadPhotoGallery() {
        val realURI = getRealPath(uri)
        val file = File(realURI)
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body = MultipartBody.Part.createFormData("picture", file.name, requestFile)
        profileViewModel.uploadPhoto(body)

    }

    private fun getRealPath(baseUri: Uri): String? {
        var cursor: Cursor? = null
        val column = "_data"
        try {
            cursor = this.context?.contentResolver?.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //permiso no aceptado por el momento
            requestPermission()
        }else{
            openGallery()
        }
    }

    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)){
            //se rechazo, volver a pedirlos cada ves q inicie la app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), codePermission)
            }
        }else{
            //pedir permisos
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), codePermission)
            }
        }
    }
}