package com.app.foodie.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.R
import com.app.foodie.models.Meal
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_row.view.*
import kotlinx.android.synthetic.main.recycler_row_cart.view.*

class CartRecyclerAdapter(private val cartArrayList : ArrayList<Meal>,private val context : Context) : RecyclerView.Adapter<CartRecyclerAdapter.PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_cart, parent, false)
        return PostHolder(itemView)
    }


    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = cartArrayList[position]
        holder.meal_name.text = item.meal_name
        holder.meal_price.text = item.meal_price.toString()
        holder.meal_count.text = item.meal_count.toString()
        holder.meal_discounted_price.text = item.meal_discounted_price.toString()
        Glide.with(context).load(cartArrayList.get(position).ImageUrl).into(holder.meal_image)
    }

    override fun getItemCount(): Int {
        return cartArrayList.size
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val meal_name : TextView = itemView.findViewById(R.id.meal_name)
        val meal_discounted_price : TextView = itemView.findViewById(R.id.meal_discounted_price)
        val meal_image : ImageView = itemView.findViewById(R.id.meal_image1)
        val meal_count : TextView = itemView.findViewById(R.id.mealCount)
        val meal_price : TextView = itemView.findViewById(R.id.mealPrice)
        //val meal_image1 : TextView = itemView.meal_image1

    }

}