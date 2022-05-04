package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.adapter.MenuRecyclerAdapter
import com.app.foodie.databinding.ActivityCustomerMainBinding
import com.app.foodie.databinding.ActivityDetailedBusinessBinding
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_customer_main.*
import kotlinx.android.synthetic.main.activity_detailed_business.*

class DetailedBusinessActivity : AppCompatActivity(), MenuRecyclerAdapter.OnItemClickListener {
    private lateinit var menuAdapter : MenuRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var mealArrayList : ArrayList<Meal>
    private lateinit var businessName : String
    private lateinit var businessImage : String
    private lateinit var businessAddress : String
    private lateinit var cartArrayList : ArrayList<Meal>
    //private lateinit var pickupTimeRange : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_business)
        var actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setTitle("Business Detail")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        mealArrayList = ArrayList<Meal>()
        getMealData()
        menuAdapter = MenuRecyclerAdapter(mealArrayList,this,this)
        menu_recyclerview.layoutManager = LinearLayoutManager(this)
        menu_recyclerview.setHasFixedSize(true)

        businessName = intent.getStringExtra("businessname").toString()
        businessAddress = intent.getStringExtra("businessaddress").toString()
        businessImage = intent.getStringExtra("businessimage").toString()
        //pickupTimeRange = intent.getStringExtra("pickuptimerange")

        business_name.text = businessName
        business_address.text = businessAddress
        Glide.with(this).load(businessImage).into(business_image)
        //business_time.text = pickupTimeRange


        if(intent !=null)
        {
            val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
            if(bundles!=null)
            {
                cartArrayList = bundles.getSerializable("cartarraylist") as ArrayList<Meal>

            }
        }
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CustomerProfileActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_home ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CustomerMainActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_map ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }
            when(it.itemId){
                R.id.nav_cart ->{
                    var bundle = Bundle()
                    bundle!!.putSerializable("cartarraylist",cartArrayList)
                    val intent = Intent(this, CartActivity::class.java)
                    intent.putExtra("businessname", businessName)
                    intent.putExtra("businessaddress", businessAddress)
                    intent.putExtra("businessimage", businessImage)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                }
            }

            true
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d("TAG", "UTKU")
                return true
            }
        }
        return super.onContextItemSelected(item)
    }





    fun detailed_business_back_clicked(view : View){
        var bundle = Bundle()
        bundle!!.putSerializable("cartarraylist",cartArrayList)
        val intent = Intent(this, CustomerMainActivity::class.java)
        intent.putExtra("businessname", businessName)
        intent.putExtra("businessaddress", businessAddress)
        intent.putExtra("businessimage", businessImage)
        intent.putExtra("bundle",bundle)
        startActivity(intent)
        finish()
    }

    override fun onItemClick(position: Int) {

        val clickedItem = mealArrayList[position]
        Toast.makeText(this, clickedItem.meal_id.toString(), Toast.LENGTH_LONG).show()
        menuAdapter.notifyItemChanged(position)
        var bundle = Bundle()
        bundle!!.putSerializable("cartarraylist",cartArrayList)
        val intent = Intent(this, DetailedMealActivity::class.java)
        /*TODO
        *  Price ve discounted price ı int olarak döndür*/

        intent.putExtra("foodname", clickedItem.meal_name)
        intent.putExtra("description", clickedItem.meal_description)
        intent.putExtra("price", clickedItem.meal_price.toString())
        intent.putExtra("discountedprice", clickedItem.meal_discounted_price.toString())
        intent.putExtra("mealid",clickedItem.meal_id)
        intent.putExtra("mealstock",clickedItem.meal_stock)
        intent.putExtra("mealimage",clickedItem.ImageUrl)
        intent.putExtra("mealposition", position)
        intent.putExtra("bundle",bundle)
        intent.putExtra("businessname", businessName)
        intent.putExtra("businessaddress", businessAddress)
        intent.putExtra("businessimage", businessImage)
        intent.putExtra("mealid",clickedItem.meal_id)
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