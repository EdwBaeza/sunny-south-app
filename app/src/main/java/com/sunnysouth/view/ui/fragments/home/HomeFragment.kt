package com.sunnysouth.view.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sunnysouth.R
import com.sunnysouth.view.ui.fragment.home.AdapterCategoryGrid
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.androidveil.VeilRecyclerFrameView
import com.sunnysouth.repository.models.Category
import com.sunnysouth.viewmodel.HomeViewModel
import java.lang.Thread.sleep


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.activity?.let { homeViewModel.context =  it }

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val veilRecyclerView: VeilRecyclerFrameView = root.findViewById(R.id.veilRecyclerView)
        textView.text = "Categorias"
        veilRecyclerView.setAdapter(AdapterCategoryGrid(listOf<Category>())) // sets your own adapter
        veilRecyclerView.setLayoutManager(GridLayoutManager(activity, 2)) // sets LayoutManager
        veilRecyclerView.addVeiledItems(10)
        veilRecyclerView.veil()
        homeViewModel.categoryPage.observe(viewLifecycleOwner, Observer {
            veilRecyclerView.apply {
                setAdapter(AdapterCategoryGrid(it.categories))
                veilRecyclerView.unVeil()
            }
        })
        homeViewModel.getCategories()

        return root
    }
}