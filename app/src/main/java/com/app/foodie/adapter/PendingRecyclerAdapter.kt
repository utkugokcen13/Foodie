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
import com.app.foodie.models.PendingOrder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_row.view.*
import kotlinx.android.synthetic.main.recycler_row_cart.view.*

class PendingRecyclerAdapter(private val pendingArrayList : ArrayList<PendingOrder>) : RecyclerView.Adapter<PendingRecyclerAdapter.PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_pending, parent, false)
        return PostHolder(itemView)
    }


    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = pendingArrayList[position]
        holder.orderBusinessAddress.text = item.orderBusinessAddress
        holder.orderBusinessName.text = item.orderBusinessName
        holder.orderDate.text = item.orderDate
        holder.profit.text = item.profit.toString()
        holder.totalAmount.text = item.totalAmount.toString()

    }

    override fun getItemCount(): Int {
        return pendingArrayList.size
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val orderBusinessAddress : TextView = itemView.findViewById(R.id.orderBusinessAddress)
        val orderBusinessName : TextView = itemView.findViewById(R.id.orderBusinessName)
        val orderDate : TextView = itemView.findViewById(R.id.orderDate)
        val profit : TextView = itemView.findViewById(R.id.orderProfit)
        val totalAmount : TextView = itemView.findViewById(R.id.orderTotalAmount)

    }

}