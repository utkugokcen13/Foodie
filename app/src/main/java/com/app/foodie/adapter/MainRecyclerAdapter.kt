package com.app.foodie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodie.databinding.ActivityCustomerSignUpBinding
import com.app.foodie.databinding.RecyclerRowBinding

class MainRecyclerAdapter(private val arrayList : ArrayList<String>) : RecyclerView.Adapter<MainRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.businessName.text = "Business Name"
        holder.binding.businessType.text = "Business Type - 5km"

    }

    override fun getItemCount(): Int {
        return 8
    }
}