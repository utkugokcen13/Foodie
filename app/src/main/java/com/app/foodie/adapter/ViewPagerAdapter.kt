package com.app.foodie.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.R
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import me.relex.circleindicator.CircleIndicator3

class ViewPagerAdapter(private var text : List<String>, private var images : List<Int>, private var colors : List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>(){

    inner class Pager2ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemImage : ImageView = itemView.findViewById(R.id.itemImagePager)
        val itemText : TextView = itemView.findViewById(R.id.itemText)
        val cardView : CardView = itemView.findViewById(R.id.cardViewProfile)
        //val indicator : CircleIndicator3 = itemView.findViewById(R.id.indicator)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
        holder.itemText.text = text[position]
        holder.cardView.setBackgroundColor(colors[position])
        //holder.indicator.setBackgroundColor(colors[position])

    }

    override fun getItemCount(): Int {
        return text.size
    }

}