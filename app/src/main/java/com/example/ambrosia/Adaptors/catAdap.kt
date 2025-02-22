package com.example.ambrosia.Adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ambrosia.Models.Category
import com.example.ambrosia.R
import com.squareup.picasso.Picasso


class catAdap(val context: Fragment, var catlist: List<Category>) :
    RecyclerView.Adapter<catAdap.MyViewHolder>(){


    lateinit var onItemClick: ((Category) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context.requireContext())
            .inflate(R.layout.itemdefine, parent, false) // Inflate your item layout
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return catlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =catlist[position]

        Log.d("TAG", "onBindViewHolder: item displayed succesfully")
        Picasso.get()
            .load(currentItem.strCategoryThumb)
            .into(holder.img)
        Log.d("TAG", "onBindViewHolder: item displayed after picasso")

//        implementing item click listner
        holder.itemView.setOnClickListener {
            Log.d("ADAPTER", "Item clicked at position $position")
            onItemClick.invoke(currentItem)
        }
    }
    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img :ImageView
        init {
            img = itemView.findViewById(R.id.rvImg)
        }
    }
}