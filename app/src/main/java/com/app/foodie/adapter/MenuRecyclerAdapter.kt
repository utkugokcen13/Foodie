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
import com.app.foodie.models.Meal
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_row.view.*
import org.w3c.dom.Text

class MenuRecyclerAdapter(private val mealArrayList : ArrayList<Meal>,
                          private val listener: MenuRecyclerAdapter.OnItemClickListener,
                          private val context : Context
) : RecyclerView.Adapter<MenuRecyclerAdapter.PostHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_business_menu, parent, false)
        return PostHolder(itemView)
    }




    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = mealArrayList[position]
        holder.meal_name.text = item.meal_name
        holder.meal_price.text = item.meal_price.toString()
        holder.meal_discounted_price.text = item.meal_discounted_price.toString()
        holder.meal_description.text = item.meal_description
        Glide.with(context).load(mealArrayList.get(position).ImageUrl).into(holder.meal_image)
        if(position == 0){
            holder.meal_name.setBackgroundColor(Color.YELLOW)
        }

    }

    override fun getItemCount(): Int {
        return mealArrayList.size
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val meal_name : TextView = itemView.findViewById(R.id.meal_name)
        val meal_price : TextView = itemView.findViewById(R.id.meal_price1)
        val meal_discounted_price : TextView = itemView.findViewById(R.id.meal_discounted_price1)
        val meal_description : TextView = itemView.findViewById(R.id.meal_description)
        val meal_image : ImageView = itemView.findViewById(R.id.meal_image1)

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