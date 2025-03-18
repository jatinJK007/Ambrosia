package com.example.ambrosia.Adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ambrosia.Models.Drink
import com.example.ambrosia.R
import com.squareup.picasso.Picasso

class DrinkAdap(val context: Fragment, var drinkList : List<Drink> ):
        RecyclerView.Adapter<DrinkAdap.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context.requireContext())
            .inflate(R.layout.itemdefine, parent, false) // Inflate your item layout
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =drinkList[position]
        val originalImageUrl = currentItem.strDrinkThumb

        // Create optimized URL with /small suffix
        val optimizedUrl = if (originalImageUrl.endsWith("/")) {
            "${originalImageUrl}small"
        } else {
            "$originalImageUrl/small"
        }

        Picasso.get()
            .load(optimizedUrl)
            .placeholder(R.drawable.round_person_24)                // Add a placeholder drawable
            .error(R.drawable.ic_launcher_background)
            .into(holder.img)

    }

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val img :ImageView
        init {
            img = itemView.findViewById(R.id.rvImg)
        }

    }
}
