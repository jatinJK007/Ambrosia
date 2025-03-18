package com.example.ambrosia.Adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ambrosia.Models.Meal
import com.example.ambrosia.R
import com.squareup.picasso.Picasso

class RecommAdap(val context : Fragment, var recommList: List<Meal>):
    RecyclerView.Adapter<RecommAdap.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommAdap.MyViewHolder {
        val itemView = LayoutInflater.from(context.requireContext())
            .inflate(R.layout.itemdefine, parent, false) // Inflate your item layout
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommAdap.MyViewHolder, position: Int) {
        val currentItem = recommList[position]
        val originalImageUrl = currentItem.strMealThumb

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

    override fun getItemCount(): Int {
        return recommList.size
    }


    class MyViewHolder(var itemView : View): RecyclerView.ViewHolder(itemView){
        val img : ImageView
        init {
            img = itemView.findViewById(R.id.rvImg)
        }
    }
}