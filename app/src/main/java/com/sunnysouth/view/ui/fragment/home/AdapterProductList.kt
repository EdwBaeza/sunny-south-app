package com.sunnysouth.view.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunnysouth.R
import com.sunnysouth.repository.models.Product
import kotlinx.android.synthetic.main.card_service.view.*

class AdapterProductList(private val listProducts: List<Product>):
    RecyclerView.Adapter<AdapterProductList.ViewHolder>() {

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        fun bindItems(product : Product){

            itemView.title_text.text = product.title
            itemView.subtitle_text.text = product.details
            itemView.type_service.text = product.type_delivery
            itemView.price.text = product.price.toString()
            itemView.time.text = product.timeOrder
            itemView.image_view_product.setImageResource(product.img)
            itemView.calification.text = product.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_service, parent, false)

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