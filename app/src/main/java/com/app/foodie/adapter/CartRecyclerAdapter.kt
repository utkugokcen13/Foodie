package com.app.foodie.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.R
import com.app.foodie.models.Meal
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.recycler_row.view.*
import kotlinx.android.synthetic.main.recycler_row_cart.view.*

class CartRecyclerAdapter(private val cartArrayList : ArrayList<Meal>,) : RecyclerView.Adapter<CartRecyclerAdapter.PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_cart, parent, false)
        return PostHolder(itemView)
    }


    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = cartArrayList[position]
        holder.meal_name.text = item.meal_name
        holder.meal_price1.text = item.meal_discounted_price.toString()
    }

    override fun getItemCount(): Int {
        return cartArrayList.size
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val meal_name : TextView = itemView.findViewById(R.id.meal_name)
        val meal_price1 : TextView = itemView.findViewById(R.id.meal_price1)
        //val meal_image1 : TextView = itemView.meal_image1

    }

}