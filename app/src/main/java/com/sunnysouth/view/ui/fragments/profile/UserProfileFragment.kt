package com.sunnysouth.view.ui.fragments.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
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
import com.sunnysouth.viewmodel.UserProfileViewModel
import com.sunnysouth.repository.models.User
import com.sunnysouth.repository.models.Profile
import com.sunnysouth.utils.getRealPath
import com.sunnysouth.utils.FactoryFile
import de.hdodenhof.circleimageview.CircleImageView


class UserProfileFragment : Fragment() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var root: View
    private lateinit var uri: Uri
    private var photoCondition = false

    private val codePermission = 777
    private val codeGallery = 123

    // UI
    private lateinit var circleImageProfile: CircleImageView
    private lateinit var btnEditNameProfile: ImageButton
    private lateinit var btnEditLastNameProfile: ImageButton
    private lateinit var btnEditPhoneNumberProfile: ImageButton
    private lateinit var btnEditBiographyProfile: ImageButton
    private lateinit var btnEditPhoto: ImageButton
    private lateinit var btnSaveData: Button
    private lateinit var txtNameProfile: TextView
    private lateinit var txtLastNameProfile: TextView
    private lateinit var txtUsernameProfile: TextView
    private lateinit var txtPhoneProfile: TextView
    private lateinit var txtEmailProfile: TextView
    private lateinit var txtBiographyProfile: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        this.activity?.let { profileViewModel.context =  it }

        this.root = inflater.inflate(R.layout.fragment_profile, container, false)
        this.circleImageProfile = root.findViewById(R.id.imageProfile)
        this.btnEditNameProfile = root.findViewById(R.id.btnEditNameProfile)
        this.btnEditLastNameProfile = root.findViewById(R.id.btnEditLastNameProfile)
        this.btnEditPhoneNumberProfile = root.findViewById(R.id.btnEditPhoneNumberProfile)
        this.btnEditPhoto = root.findViewById(R.id.btnEditPhoto)
        this.btnEditBiographyProfile = root.findViewById(R.id.btnEditBiographyProfile)
        this.btnSaveData = root.findViewById(R.id.btnSaveData)
        this.txtNameProfile = root.findViewById(R.id.txtFirstNameProfile)
        this.txtLastNameProfile = root.findViewById(R.id.txtLastNameProfile)
        this.txtUsernameProfile = root.findViewById(R.id.txtUsernameProfile)
        this.txtPhoneProfile = root.findViewById(R.id.txtPhoneProfile)
        this.txtEmailProfile = root.findViewById(R.id.txtEmailProfile)
        this.txtBiographyProfile = root.findViewById(R.id.txtBiographyProfile)

        profileViewModel.user.observe(
            viewLifecycleOwner,
            Observer {
                observeUser(it)
            }
        )

        profileViewModel.userError.observe(
            viewLifecycleOwner,
            Observer {
                printError(it)
            }
        )

        profileViewModel.getCurrentUser()

        btnEditNameProfile.setOnClickListener {
            buildAlertDialog(
                R.layout.fragment_edit_first_name_profile,
                R.id.editName,
                R.id.txtFirstNameProfile,
                R.id.btnActualizeFirstName
            )
        }

        btnEditLastNameProfile.setOnClickListener {
            buildAlertDialog(
                R.layout.fragment_edit_last_name_profile,
                R.id.editLastName,
                R.id.txtLastNameProfile,
                R.id.btnActualizeLastName
            )
        }

        btnEditPhoneNumberProfile.setOnClickListener {
            buildAlertDialog(
                R.layout.fragment_edit_phone_number_profile,
                R.id.editPhoneNumber,
                R.id.txtPhoneProfile,
                R.id.btnActualizePhoneNumber
            )
        }

        btnEditBiographyProfile.setOnClickListener {
            buildAlertDialog(
                R.layout.fragment_edit_biography_profile,
                R.id.editBiography,
                R.id.txtBiographyProfile,
                R.id.btnActualizeBiography
            )
        }

        btnSaveData.setOnClickListener(::update)
        btnEditPhoto.setOnClickListener(::checkPermission)

        return root
    }

    private fun observeUser(user: User){
        txtUsernameProfile.text = user.username
        txtNameProfile.text = user.firstName
        txtLastNameProfile.text = user.lastName
        txtEmailProfile.text = user.email
        txtPhoneProfile.text = user.phoneNumber
        txtBiographyProfile.text = user.profile.biography


        if (user.profile.picture.isNullOrEmpty()){
            circleImageProfile.setImageResource(R.drawable.profile_default)
            return
        }

        Picasso.with(context)
            .load(user.profile.picture)
            .placeholder(R.drawable.profile_default)
            .error(R.drawable.profile_error)
            .into(circleImageProfile)
    }

    private fun printError(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun buildAlertDialog(fragmentId: Int, editTextId: Int, textViewId: Int, btnSaveDataId: Int) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val v: View = inflater.inflate(fragmentId, null)
        val textView: TextView = this.root.findViewById(textViewId)
        val editText: EditText = v.findViewById(editTextId)

        editText.setText(textView.text)
        builder.setView(v)
        var dialog: AlertDialog = builder.create()
        dialog.show()

        (v.findViewById(btnSaveDataId) as Button).setOnClickListener{
            textView.text = editText.text.toString()
            dialog.cancel()
        }
    }

    private fun update(view: View): Unit {
        val user = User()
        user.profile = Profile()
        user.profile.biography = txtBiographyProfile.text.toString()
        user.firstName = txtNameProfile.text.toString()
        user.lastName = txtLastNameProfile.text.toString()
        user.phoneNumber = txtPhoneProfile.text.toString()

        profileViewModel.update(user)

        if(photoCondition){
            uploadPictureProfile()
        }

        Toast.makeText(activity, "Datos actualizados", Toast.LENGTH_LONG).show()
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

    private fun uploadPictureProfile() {
        val realURI = getRealPath(this.context, this.uri)
        profileViewModel.uploadPhoto(FactoryFile.buildMultiPartFormData(realURI, "picture", "image/jpeg"))
    }

    private fun checkPermission(view: View): Unit {
        if(ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermission()
        }else{
            openGallery()
        }
    }

    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
            || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)){
            //permisisions denied
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), codePermission)
            }
        }else{
            // Permissions requested
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), codePermission)
            }
        }
    }
}