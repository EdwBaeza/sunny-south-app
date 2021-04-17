package com.sunnysouth.view.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunnysouth.R
import com.sunnysouth.utils.AutoFitGridLayoutManager
import com.sunnysouth.view.ui.fragment.home.AdapterCategoryGrid
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        this.activity?.let { homeViewModel.context =  it }

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        textView.text = "Sunny South"


        homeViewModel.categoryPage.observe(this, Observer {
            recycler_category_grid.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = AutoFitGridLayoutManager(activity, 2)
                adapter = AdapterCategoryGrid(it.categories)
                isNestedScrollingEnabled = false
            }
        })
        homeViewModel.getCategories()

        return root
    }
}