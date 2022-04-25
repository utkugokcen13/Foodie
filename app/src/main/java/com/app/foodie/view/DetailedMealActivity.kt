package com.app.foodie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.foodie.R
import kotlinx.android.synthetic.main.activity_detailed_meal.*

class DetailedMealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_meal)

        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")
        val pickupTimeRange = intent.getStringExtra("pickuptimerange")

        val meal_name = intent.getStringExtra("foodname")
        val meal_description = intent.getStringExtra("description")
        val meal_price = intent.getStringExtra("price")
        val meal_discounted_price = intent.getStringExtra("discountedprice")

        Log.d("TAG", meal_discounted_price.toString())


        food_name.text = meal_name
        food_description.text = meal_description
        meal_price1.text = meal_price
        meal_discounted_price1.text = meal_discounted_price
        meal_count.text = "1"
        total_price.text = meal_price1.text
    }
}


