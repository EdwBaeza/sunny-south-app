package com.sunnysouth.view.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.sunnysouth.R
import com.sunnysouth.repository.models.User
import com.sunnysouth.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class UserProfileFragment : Fragment() {

    private lateinit var profileViewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        this.activity?.let { profileViewModel.setContextApp(it) }

        profileViewModel.user.observe(this, Observer<User> {
            Log.i("user", it.email)
        })

        profileViewModel.getSessionUser()

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val imagenProfile: ImageView = root.findViewById(R.id.imageProfile)
        val btnEditProfile : ImageButton = root.findViewById(R.id.btnEditProfile)
        val txtNameProfile : TextView = root.findViewById(R.id.txtNameProfile)
        val txtLastNameProfile : TextView = root.findViewById(R.id.txtLastNameProfile)

        Picasso.with(context)
            .load("https://www.lavanguardia.com/r/GODO/LV/p7/WebSite/2020/04/14/Recortada/img_psola_20180724-165630_imagenes_lv_terceros_elite_101_unit_0049_r-kCsG-U48496101636McD-992x558@LaVanguardia-Web.jpg")
            .placeholder(R.drawable.fondo_mexico)
            .error(R.drawable.fondo_mexico)
            .into(imagenProfile);

        btnEditProfile.setOnClickListener {
            /*val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Titulo")
            builder.setMessage("Mensaje")
            builder.setPositiveButton("Aceptar", null)

            val dialog: AlertDialog = builder.create()
            dialog.show()*/

            val builder = AlertDialog.Builder(activity)
            val inflater = activity!!.layoutInflater
            val v: View = inflater.inflate(R.layout.fragment_edit_profile, null)

            val editName : EditText = v.findViewById(R.id.editName)
            val editLastName : EditText = v.findViewById(R.id.editLastName)

            editName.setText(""+txtNameProfile.text)
            editLastName.setText(""+txtLastNameProfile.text)

            builder.setView(v)
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return root
    }

}