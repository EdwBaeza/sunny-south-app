package com.sunnysouth.view.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sunnysouth.R
import com.sunnysouth.repository.models.Category
import kotlinx.android.synthetic.main.card_category.view.*

class AdapterCategoryGrid(private val listOfCategories: List<Category>):
    RecyclerView.Adapter<AdapterCategoryGrid.ViewHolder>() {

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        fun bindItems(category : Category){

            itemView.txtCategoryName.text = category.name
            Picasso.with(itemView.context)
                .load(category.picture)
                .placeholder(R.drawable.profile_default)
                .error(R.drawable.profile_error)
                .into(itemView.imageCategory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return listOfCategories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listOfCategories[position])
    }
}