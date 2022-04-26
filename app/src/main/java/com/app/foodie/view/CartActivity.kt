package com.app.foodie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodie.R
import com.app.foodie.adapter.CartRecyclerAdapter
import com.app.foodie.adapter.MainRecyclerAdapter
import com.app.foodie.models.Business
import com.app.foodie.models.Meal
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_main.*

//TODO SharedPreferences kullanarak carttaki ürünleri save et.

class CartActivity : AppCompatActivity() {
    private lateinit var cartArrayList : ArrayList<Meal>
    private lateinit var cartAdapter : CartRecyclerAdapter
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        if(intent !=null)
        {
            val bundles:Bundle = intent.extras!!.get("bundle") as Bundle
            if(bundles!=null)
            {
                cartArrayList = bundles.getSerializable("cartarraylist") as ArrayList<Meal>

            }
        }

        cartAdapter = CartRecyclerAdapter(cartArrayList)
        RecyclerViewCart.layoutManager = LinearLayoutManager(this)
        RecyclerViewCart.setHasFixedSize(true)
        RecyclerViewCart.adapter = cartAdapter

        val businessName = intent.getStringExtra("businessname")
        val businessAddress = intent.getStringExtra("businessaddress")


        businessNameCart.text = businessName
        businessAddressCart.text = businessAddress

        //val cartArrayList = intent.getSerializableExtra("cartarraylist" )


        Log.d("TAG", cartArrayList.get(0).meal_name)

    }
}