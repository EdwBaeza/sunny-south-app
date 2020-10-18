package com.sunnysouth.view.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunnysouth.R
import com.sunnysouth.repository.models.ProductMain
import kotlinx.android.synthetic.main.card_product.view.*

class AdapterProductMain(private val listProducts: List<ProductMain>):
    RecyclerView.Adapter<AdapterProductMain.ViewHolder>() {

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        fun bindItems(product : ProductMain){

            itemView.title_text.text = product.title
            itemView.subtitle_text.text = product.details
            itemView.type_delivery_text.text = product.type_delivery
            itemView.image_view_product.setImageResource(product.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false)

        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {

        return listProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listProducts[position])
    }
}