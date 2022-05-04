package com.app.foodie.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.R
import com.app.foodie.models.Business
import com.app.foodie.view.CustomerMainActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_row.view.*

class MainRecyclerAdapter(
    private val businessArrayList : ArrayList<Business>,
    private val listener: OnItemClickListener,
    private val context : Context) : RecyclerView.Adapter<MainRecyclerAdapter.PostHolder>() {
    //private lateinit var mListener : onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return PostHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = businessArrayList[position]
        holder.businessName.text = item.businessName
        holder.businessType.text = item.businessType
        holder.ratingPoint.text = item.avgRating.toString()
        holder.businessName.transitionName = item.businessID.toString()
        Glide.with(context).load(businessArrayList.get(position).ImageUrl).into(holder.businessImage)
        if(position == 0){
            holder.businessName.setBackgroundColor(Color.YELLOW)
        }


    }

    override fun getItemCount(): Int {
        return businessArrayList.size
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val businessName : TextView = itemView.businessName
        val businessType : TextView = itemView.businessType
        val ratingPoint : TextView = itemView.ratingPoint
        val businessImage : ImageView = itemView.businessImage


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }

        }


    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}