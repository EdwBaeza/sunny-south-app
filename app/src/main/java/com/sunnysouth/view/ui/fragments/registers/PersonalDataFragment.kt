package com.sunnysouth.view.ui.fragments.registers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sunnysouth.R
import com.sunnysouth.repository.models.User
import com.sunnysouth.view.ui.activity.RegisterActivity
import kotlinx.android.synthetic.main.fragment_personal_data.*
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*var viewModel = (this.activity as RegisterActivity?)?.viewModel
        var user = (this.activity as RegisterActivity?)?.viewModel?.user?.value

        user?.firstName = this.first_name?.text.toString()
        user?.lastName = this.last_name?.text.toString()
        user?.phoneNumber = this.phone_number?.text.toString()

        (this.activity as RegisterActivity?)?.viewModel?.setUser(user!!)*/
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_data, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonalDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PersonalDataFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
