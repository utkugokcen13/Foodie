package com.app.foodie.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.R
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import com.app.foodie.databinding.RecyclerRowBinding
import com.app.foodie.models.Business
import com.app.foodie.models.Meal

class MenuRecyclerAdapter(private val mealArrayList : ArrayList<Meal>,
                          private val listener: MenuRecyclerAdapter.OnItemClickListener
) : RecyclerView.Adapter<MenuRecyclerAdapter.PostHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_business_menu, parent, false)
        return PostHolder(itemView)
    }




    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = mealArrayList[position]
        holder.mealName.text = item.mealName
        holder.mealPrice.text = item.mealprice.toString()
        holder.mealDiscountedPrice.text = item.mealDiscountedPrice.toString()
        if(position == 0){
            holder.mealName.setBackgroundColor(Color.YELLOW)
        }

    }

    override fun getItemCount(): Int {
        return mealArrayList.size
    }

    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val mealName : TextView = itemView.findViewById(R.id.meal_name)
        val mealPrice : TextView = itemView.findViewById(R.id.meal_price)
        val mealDiscountedPrice : TextView = itemView.findViewById(R.id.meal_discounted_price)

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