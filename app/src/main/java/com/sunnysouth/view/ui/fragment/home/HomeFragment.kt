package com.sunnysouth.view.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnysouth.R
import com.sunnysouth.repository.models.ProductMain
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val products = listOf<ProductMain>(
        ProductMain("Raising Arizona", "No se que va aqui", "Con envio", R.drawable.sunnysouthlogo),
        ProductMain("Vampire's Kiss", "No se que va aqui", "Con envio", R.drawable.sunnysouthlogo),
        ProductMain("Con Air", "No se que va aqui", "Solo en tienda", R.drawable.sunnysouthlogo),
        ProductMain("Gone in 60 Seconds", "No se que va aqui", "Solo en tienda", R.drawable.sunnysouthlogo),
        ProductMain("National Treasure", "No se que va aqui", "Apartado, con entrega en tienda", R.drawable.sunnysouthlogo)
    )


    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.ubication_to_send)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        recycler_product_main.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
            // set the custom adapter to the RecyclerView
            adapter =
                AdapterProductMain(products)
        }
    }

}