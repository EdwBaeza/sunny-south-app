package com.sunnysouth.view.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnysouth.R
import com.sunnysouth.repository.models.Product
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val products = listOf<Product>(
        Product(title = "Raising Arizona", subtitle = "nameEnterprise", details = "No se que va aqui", timeOrder = "1h" ,type_delivery = "Con envio", price = 89.00 , img = R.drawable.sunnysouthlogo, score = 3.5),
        Product(title = "Vampire's Kiss", subtitle = "nameEnterprise", details = "No se que va aqui", timeOrder = "1h", type_delivery = "Con envio", price = 89.00 , img = R.drawable.sunnysouthlogo, score = 3.5),
        Product(title = "Con Air", subtitle = "nameEnterprise", details =  "No se que va aqui", timeOrder = "1h", type_delivery =  "Solo en tienda", price = 89.00 , img = R.drawable.sunnysouthlogo, score = 3.5),
        Product(title = "Gone in 60 Seconds", subtitle = "nameEnterprise", details = "No se que va aqui", timeOrder = "1h", type_delivery = "Solo en tienda", price = 89.00 , img = R.drawable.sunnysouthlogo, score = 3.5),
        Product(title = "National Treasure", subtitle = "nameEnterprise", details = "No se que va aqui", timeOrder = "1h", type_delivery = "Apartado, con entrega en tienda", price = 89.00 , img = R.drawable.sunnysouthlogo, score = 3.5)
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
            adapter = AdapterProductMain(products)
        }
        recycler_product_grid.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = GridLayoutManager(activity, 2)
            adapter = AdapterProductMain(products)
        }
        recycler_product_list.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
            // set the custom adapter to the RecyclerView
            adapter = AdapterProductMain(products)
        }


    }

}