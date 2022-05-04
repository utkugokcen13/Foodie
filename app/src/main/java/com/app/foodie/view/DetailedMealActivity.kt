package com.app.foodie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.foodie.R
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detailed_business.*
import kotlinx.android.synthetic.main.activity_detailed_meal.*

class DetailedMealActivity : AppCompatActivity() {
    var count = 1
    private var meal_id : Int = 0
    private var meal_stock : Int = 5
    private var meal_position : Int = 0
    private var meal_name : String = ""
    private var meal_description : String = ""
    private var meal_price : String = ""
    private var meal_discounted_price : String = ""
    private var meal_image : String = ""
    private lateinit var businessName : String
    private lateinit var businessAddress : String
    private lateinit var businessImage : String
    private lateinit var database: DatabaseReference
    private lateinit var cartArrayList : ArrayList<Meal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_meal)
        var actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setTitle("Food Detail")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        cartArrayList = ArrayList<Meal>()

        businessName = intent.getStringExtra("businessname").toString()
        businessAddress = intent.getStringExtra("businessaddress").toString()
        businessImage = intent.getStringExtra("businessimage").toString()
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")

        meal_name = intent.getStringExtra("foodname").toString()
        meal_description = intent.getStringExtra("description").toString()
        meal_image = intent.getStringExtra("mealimage").toString()
        meal_price = intent.getStringExtra("price").toString()
        meal_discounted_price = intent.getStringExtra("discountedprice").toString()
        meal_stock = intent.getIntExtra("mealstock",5)
        meal_position = intent.getIntExtra("mealposition",0)
        meal_id = intent.getIntExtra("mealid",0)

        Log.d("TAG", meal_stock.toString())


        food_name.text = meal_name
        food_description.text = meal_description
        meal_price1.text = meal_price
        meal_discounted_price1.text = meal_discounted_price
        meal_count.text = count.toString()
        total_price.text = meal_discounted_price
        Glide.with(this).load(meal_image).into(meal_image1)
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

    fun plusClicked(view : View){
        count = count + 1
        meal_count.text = count.toString()
        val str = meal_discounted_price1.text.toString()
        total_price.text = (count * str.toDouble()).toString()
    }

    fun addToCartClicked(view : View){
        //TODO referans name i duzelt
        database = FirebaseDatabase.getInstance().getReference("Business").child("Emre").child("menu").child(meal_position.toString())
        if(meal_stock == 0){
            Toast.makeText(this, "This meal is out of stock.", Toast.LENGTH_SHORT).show()
        }else{
            val mealStock = mapOf<String,Int>("meal_stock" to meal_stock - count)
            database.updateChildren(mealStock)
            //TODO image url düzelt

            val meal = Meal(meal_name,meal_id,meal_image,meal_description,meal_price.toDouble(),meal_discounted_price.toDouble(),3.4,meal_stock,count)
            cartArrayList.add(meal)
            Toast.makeText(this, "Meal added to your cart.", Toast.LENGTH_SHORT).show()
            //meal_name,meal_id.toInt(),"asdas",meal_description,meal_price.toDouble(),meal_discounted_price.toDouble(),30.0,meal_stock
        }
    }

    fun detailed_meal_back_clicked(view : View){
        var bundle = Bundle()
        bundle!!.putSerializable("cartarraylist",cartArrayList)
        val intent = Intent(this, DetailedBusinessActivity::class.java)
        intent.putExtra("businessname", businessName)
        intent.putExtra("businessaddress", businessAddress)
        intent.putExtra("businessimage", businessImage)
        intent.putExtra("bundle",bundle)
        startActivity(intent)
        finish()
    }
}


