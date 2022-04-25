package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.adapter.MenuRecyclerAdapter
import com.app.foodie.databinding.ActivityCustomerMainBinding
import com.app.foodie.databinding.ActivityDetailedBusinessBinding
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.activity_detailed_business.*

class DetailedBusinessActivity : AppCompatActivity(), MenuRecyclerAdapter.OnItemClickListener {
    private lateinit var menuAdapter : MenuRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var mealArrayList : ArrayList<Meal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_business)
        mealArrayList = ArrayList<Meal>()
        getMealData()
        menuAdapter = MenuRecyclerAdapter(mealArrayList,this)
        menu_recyclerview.layoutManager = LinearLayoutManager(this)
        menu_recyclerview.setHasFixedSize(true)

        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")

        business_name.text = businessName
        business_address.text = businessAddress
        business_time.text = pickupTimeRange



    }

    fun detailed_business_back_clicked(view : View){
        val intent = Intent(this, CustomerMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onItemClick(position: Int) {

        val clickedItem = mealArrayList[position]
        Toast.makeText(this, clickedItem.meal_id.toString(), Toast.LENGTH_LONG).show()
        menuAdapter.notifyItemChanged(position)
        val intent = Intent(this, DetailedMealActivity::class.java)
        /*TODO
        *  Price ve discounted price ı int olarak döndür*/

        intent.putExtra("foodname", clickedItem.meal_name)
        intent.putExtra("description", clickedItem.meal_description)
        intent.putExtra("price", clickedItem.meal_price.toString())
        intent.putExtra("discountedprice", clickedItem.meal_discounted_price.toString())
        startActivity(intent)
        finish()
    }

    private fun getMealData(){
        database = FirebaseDatabase.getInstance().getReference("Business").child("Emre").child("menu")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (mealSnapShot in snapshot.children){
                        val meal = mealSnapShot.getValue(Meal::class.java)
                        Log.d("TAG", meal!!.meal_discounted_price.toString())
                        mealArrayList.add(meal!!)
                    }
                    menu_recyclerview.adapter = menuAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}